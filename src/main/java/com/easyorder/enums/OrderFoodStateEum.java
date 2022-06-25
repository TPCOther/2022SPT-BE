package com.easyorder.enums;

public enum OrderFoodStateEum {
	PROCESSING(0,"处理中"),COMPLETE(1,"已完成");
	private int state;
	private String stateInfo;

	private OrderFoodStateEum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public static OrderFoodStateEum stateOf(int state) {
		for (OrderFoodStateEum stateEnum : values()) {
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
