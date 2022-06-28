package com.easyorder.enums;


public enum ExecuteStateEum {
    SUCCESS(1111,"操作成功"),INNER_ERROR(1000,"操作失败"),
    EMPTY(1001,"结果为空"),INCOMPLETE(1002,"输入信息不完整"),INVALID(1003,"输入数据不合法");

    private int state;
    private String stateInfo;
    
    private ExecuteStateEum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ExecuteStateEum stateOf(int state){
        for(ExecuteStateEum stateEnum : values()){
            if(stateEnum.getState() == state){
                return stateEnum;
            }
        }
        return null;
    }

    public int getState(){
        return state;
    }
    public String getStateInfo(){
        return stateInfo;
    }
}

