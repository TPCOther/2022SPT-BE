package com.easyorder.enums;

public enum FoodStateEum {
	SALING(1,"正在销售中"),EMPTY(2,"已售罄"),BAN(0,"已下架");
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
