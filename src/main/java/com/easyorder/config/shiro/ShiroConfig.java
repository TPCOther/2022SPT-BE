package com.easyorder.config.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration  //将shiro整合到spring中的相关配置
public class ShiroConfig {
    @Bean("securityManager")
    public SecurityManager securityManager(OAuth2Realm realm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setRememberMeManager(null);
        return securityManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, OAuth2Filter filter){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        Map<String, Filter> map = new HashMap<>();
        map.put("oauth2",filter);
        shiroFilter.setFilters(map);

        Map<String, String> filterMap = new LinkedHashMap<>();  //设置要拦截的路径
        //不被拦截
        // filterMap.put("/webjars/**","anon");
        // filterMap.put("/druid/**","anon");
        // filterMap.put("/app/**","anon");
        // filterMap.put("/sys/login","anon");
        // filterMap.put("/swagger/**","anon");
        // filterMap.put("/v2/api-docs","anon");
        // filterMap.put("/swagger-ui.html","anon");
        // filterMap.put("/swagger-resources/**","anon");
        // filterMap.put("/captcha.jpg","anon");
        // filterMap.put("/user/register","anon");
        // filterMap.put("/user/login","anon");
        // filterMap.put("/test/**","anon");
        // filterMap.put("/**","anon");
        filterMap.put("/hello","anon");
        filterMap.put("/test","anon");
        //拦截
        filterMap.put("/**","oauth2");
        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    @Bean("authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}