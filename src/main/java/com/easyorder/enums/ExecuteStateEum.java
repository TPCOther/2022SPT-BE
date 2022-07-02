package com.easyorder.enums;

public enum ExecuteStateEum {
    SUCCESS(200,"操作成功"),INNER_ERROR(500,"操作失败,内部错误"),
    EMPTY(500,"查询结果为空"),INCOMPLETE(500,"输入信息不完整"),
    INPUT_ERROR(500,"输入数据不合法");

    
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

