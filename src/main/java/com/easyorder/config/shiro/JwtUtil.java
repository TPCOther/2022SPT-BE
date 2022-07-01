package com.easyorder.config.shiro;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

//完成JWT相关方法实现
@Component
@Slf4j
public class JwtUtil {
    @Value("${emos.jwt.secret}") //注入配置信息
    private String secret;

    @Value("${emos.jwt.expire}")
    private int expire;

    public String createToken(Long staffId){
        Date date = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR,5); //计算过期日期
        Algorithm algorithm = Algorithm.HMAC256(secret); //选择加密算法
        JWTCreator.Builder builder = JWT.create(); //建造者模式生成token
        String token = builder.withClaim("staffId",staffId).withExpiresAt(date).sign(algorithm);
        return token; //组装token并返回
    }

    public int getStaffId(String token){
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("staffId").asInt();
    }

    public void verifierToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build(); //创建对应算法的验证器
        verifier.verify(token); //利用验证器验证token，不成功时抛出RuntimeException
    }
}

