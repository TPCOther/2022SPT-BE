package com.easyorder.enums;

public enum OrderStateEum {
	CANCEL(0,"已取消"),PENDING(1,"待处理"),PROCESSING(2,"处理中"),UNPAY(3,"未支付"),COMPLETE(4,"已完成");
	private int state;
	private String stateInfo;

	private OrderStateEum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public static OrderStateEum stateOf(int state) {
		for (OrderStateEum stateEnum : values()) {
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
