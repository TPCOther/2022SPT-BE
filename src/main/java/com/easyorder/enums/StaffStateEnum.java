package com.easyorder.enums;

public enum StaffStateEnum {
    RESIGN(-1,"已离职"),UNAVAILABLE(0,"暂不可用"),WROKING(1,"工作中");

    private int state;
    private String stateInfo;
    
    private StaffStateEnum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static StaffStateEnum stateOf(int state){
        for(StaffStateEnum stateEnum : values()){
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
