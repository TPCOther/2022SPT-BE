package com.easyorder.enums;

public enum FoodStateEum {
	SALING(1,"正在销售中"),SALEOUT(2,"已售罄"),BAN(0,"已下架"),
	SUCCESS(1001,"操作成功"),ERROR(1002,"操作失败"),EMPTYMSG(1003,"请输入所有信息");
	private int state;
	private String stateInfo;

	private FoodStateEum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public static FoodStateEum stateOf(int state) {
		for (FoodStateEum stateEnum : values()) {
			if (stateEnum.getState() == state)
				return stateEnum;
		}
		return null;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

}
