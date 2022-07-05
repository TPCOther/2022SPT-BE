package com.easyorder.config.shiro;

import org.springframework.stereotype.Component;

/**
 * 储存令牌的媒介类，使得filter与AOP切面类通信
 */
@Component
public class ThreadLocalToken {
    private ThreadLocal<String> local = new ThreadLocal();

    public void setToken(String token){
        local.set(token);
    }

    public String getToken(){
        return local.get();
    }

    public void clear(){
        local.remove();
    }
}
