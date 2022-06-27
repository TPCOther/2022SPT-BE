package com.easyorder.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class RBody extends HashMap<String, Object>{
	public RBody(){
        put("code", HttpStatus.OK.value());
        put("msg","success");
    }

    public RBody put(String key, Object value){ //方便链式调用添加信息
        super.put(key, value);
        return this;
    }

    public static RBody ok(){
        return new RBody();
    }

    public static RBody ok(String msg){
        RBody r=new RBody();
        r.put("msg",msg);
        return r;
    }

    public static RBody ok(Map<String,Object> map){
        RBody r=new RBody();
        r.putAll(map);
        return r;
    }

    public static RBody error(int code, String msg){
        RBody r=new RBody();
        r.put("code",code);
        r.put("msg",msg);
        return r;
    }

    public static RBody error(String msg){
       return error(HttpStatus.INTERNAL_SERVER_ERROR.value(),msg);
    }

    public static RBody error(){
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(),"未知错误，请联系管理员");
    }
}
