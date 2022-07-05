package com.easyorder.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

//将jwt-token封装为令牌对象供shiro使用
public class OAuth2Token implements AuthenticationToken {
    private String token;

    public OAuth2Token(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
