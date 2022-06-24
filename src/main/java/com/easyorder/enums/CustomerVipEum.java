package com.easyorder.enums;

public enum CustomerVipEum {
	VIP(1,"会员"),NOTVIP(0,"非会员");
	private int state;
	private String stateInfo;

	private CustomerVipEum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public static CustomerVipEum stateOf(int state) {
		for (CustomerVipEum stateEnum : values()) {
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
