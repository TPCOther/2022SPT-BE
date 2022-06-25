package com.easyorder.enums;

public enum PermissionEum {
	BAN(0,"已禁用"),NORMAL(1,"正常");
	private int state;
	private String stateInfo;

	private PermissionEum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public static PermissionEum stateOf(int state) {
		for (PermissionEum stateEnum : values()) {
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
