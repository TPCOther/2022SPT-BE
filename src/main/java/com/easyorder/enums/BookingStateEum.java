package com.easyorder.enums;

public enum BookingStateEum {
	CANCEL(0,"已取消"),Booking(1,"已预定"),COMPLETE(2,"已完成");
	private int state;
	private String stateInfo;

	private BookingStateEum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public static BookingStateEum stateOf(int state) {
		for (BookingStateEum stateEnum : values()) {
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
