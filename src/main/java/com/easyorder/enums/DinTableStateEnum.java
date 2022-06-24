package com.easyorder.enums;

public enum DinTableStateEnum {
    BAN(-1,"不可用"),IDLE(0,"空闲"),USING(1,"使用中"),BOOKING(2,"预约中");

    private int state;
    private String stateInfo;
    
    private DinTableStateEnum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static DinTableStateEnum stateOf(int state){
        for(DinTableStateEnum stateEnum : values()){
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
