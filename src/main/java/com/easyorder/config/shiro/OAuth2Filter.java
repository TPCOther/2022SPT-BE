package com.easyorder.config.shiro;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
@Scope("prototype") //使得filter变为多例以使用ThreadLocal
public class OAuth2Filter extends AuthenticatingFilter {
    @Autowired
    private ThreadLocalToken threadLocalToken;

    @Value("${easyorder.jwt.cache-expire}")
    private int cacheExpire;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    

    //判断拦截的请求是否需要shiro处理，其中options请求无需认证
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {   
        HttpServletRequest req = (HttpServletRequest) request;
        // ajax提交application/json数据时会先发出option请求
        //这里需要放行options请求，不需要shiro处理-true就放行
        return req.getMethod().equals(RequestMethod.OPTIONS.name());
    }

    //验证令牌是否能通过
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {   
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Credentials","true");          //设置跨域请求运行
        resp.setHeader("Access-Control-Allow-Origin",req.getHeader("Origin"));

        threadLocalToken.clear();

        String token = getRequestToken(req);
        if(StrUtil.isBlank(token)){
            resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
            resp.getWriter().print("无效的令牌");
            return false;
        }
        try{
            jwtUtil.verifierToken(token);
        }catch (TokenExpiredException e){   //客户端令牌过期，验证服务端令牌是否过期，如果无则刷新令牌
            if(redisTemplate.hasKey(token)){
                redisTemplate.delete(token);
                Long staffId = jwtUtil.getStaffId(token);
                token = jwtUtil.createToken(staffId);
                redisTemplate.opsForValue().set(token,staffId+"",cacheExpire, TimeUnit.DAYS);
                threadLocalToken.setToken(token);
            }
            else{
                resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
                resp.getWriter().print("令牌已过期");
                return false;
            }
        }catch (JWTDecodeException e){  //令牌内容出错
            resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
            resp.getWriter().print("无效的令牌");
            return false;
        }
        //间接调用realm类
        boolean bool=executeLogin(request,response); //返回认证结果
        return bool;
    }
    //认证失败
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Credentials","true");
        resp.setHeader("Access-Control-Allow-Origin",req.getHeader("Origin"));
        resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
        try{
            resp.getWriter().print(e.getMessage());
        } catch (Exception exception){ }
        return false;
    }

    //获取其请求中的token
    private String getRequestToken(HttpServletRequest request){
        String token = request.getHeader("token");
        if(StrUtil.isBlank(token)){
            token = request.getParameter("token");
        }
        return token;
    }

    //从拦截请求中创建令牌对象以便shiro处理
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception { 
        //获取请求token 
        HttpServletRequest req = (HttpServletRequest) request;
        String token = getRequestToken(req);
        if(StrUtil.isBlank(token)){
            return null;
        }
        return new OAuth2Token(token);
    }
}
