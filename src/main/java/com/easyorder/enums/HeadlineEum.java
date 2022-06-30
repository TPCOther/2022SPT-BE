package com.easyorder.enums;

public enum HeadlineEum {
	USING(1,"使用中"),NOTUSING(0,"未使用中");
	private int state;
	private String stateInfo;

	private HeadlineEum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public static HeadlineEum stateOf(int state) {
		for (HeadlineEum stateEnum : values()) {
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
