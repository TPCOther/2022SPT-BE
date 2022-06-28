package com.easyorder.util;

import java.util.HashMap;
import java.util.Map;

import com.easyorder.enums.ExecuteStateEum;

public class RBody extends HashMap<String, Object>{
	public RBody(){ 
        put("code", ExecuteStateEum.SUCCESS.getState());
        put("msg",ExecuteStateEum.SUCCESS.getStateInfo()); 
    }

    //链式调用设置data,msg,others
    public RBody put(String key, Object value){ //方便链式调用添加信息
        super.put(key, value);
        return this;
    }
    public RBody msg(String msg){
        super.put("msg",msg);
        return this;
    }
    public RBody data(Object value){
        super.put("data",value);
        return this;
    }

    /*
     * 操作成功，自动设置状态为SUCCESS，可单独传入msg进行描述修改
     */
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

    /*
     * 操作失败，自动设置状态为INNER_ERROR，可单独传入msg进行描述修改
     */
    public static RBody error(){
        RBody r = new RBody();
        r.put("code", ExecuteStateEum.INNER_ERROR.getState());
        r.put("msg",ExecuteStateEum.INNER_ERROR.getStateInfo()); 
        return r;
    }
    public static RBody error(String msg){
        RBody r = error();
        r.put("msg",msg);
        return r;
    }

    /*
     * 其他操作情况，传入ExecuteStateEum
     */
    public static RBody executeState(ExecuteStateEum eum){
        RBody r=new RBody();
        r.put("code",eum.getState());
        r.put("msg",eum.getStateInfo());
        return r;
    }

}
