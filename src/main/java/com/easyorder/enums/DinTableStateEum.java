package com.easyorder.enums;

public enum DinTableStateEum {
    BAN(-1,"不可用"),IDLE(0,"空闲"),USING(1,"使用中"),BOOKING(2,"预约中");

    private int state;
    private String stateInfo;
    
    private DinTableStateEum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static DinTableStateEum stateOf(int state){
        for(DinTableStateEum stateEnum : values()){
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
