package com.pig8.api.platform.global;

public enum MessageStatus {
	NOT_READ("未读"),
	READ("已读"),
	END("错误状态");
	private String desc;

	MessageStatus(String desc) {
		this.desc = desc;
	}

	public String toString() {
		return desc;
	}
	public static MessageStatus valueOf(int value) {
		switch (value) {
			case 0:
				return NOT_READ;
			case 1:
				return READ;
			case 2:
				return END;
			default:
				return END;
			}
		}
	public static MessageStatus toValue(String value) {
		value = value.toLowerCase();
		if (value.equals(NOT_READ.name().toLowerCase())){
			return NOT_READ;
		}
		if (value.equals(READ.name().toLowerCase())){
			return READ;
		}
		if (value.equals(END.name().toLowerCase())){
			return END;
		}
		return END;
	}
}
