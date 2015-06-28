package com.pig8.api.platform.global;

public enum VerfyStatus {
	NOT_VERIFIED("未验证"),
	VERIFIED("已验证"),
	END("错误状态");
	private String desc;

	VerfyStatus(String desc) {
		this.desc = desc;
	}

	public String toString() {
		return desc;
	}
	public static VerfyStatus valueOf(int value) {
		switch (value) {
			case 0:
				return NOT_VERIFIED;
			case 1:
				return VERIFIED;
			case 2:
				return END;
			default:
				return END;
			}
		}
	public static VerfyStatus toValue(String value) {
		value = value.toLowerCase();
		if (value.equals(NOT_VERIFIED.name().toLowerCase())){
			return NOT_VERIFIED;
		}
		if (value.equals(VERIFIED.name().toLowerCase())){
			return VERIFIED;
		}
		if (value.equals(END.name().toLowerCase())){
			return END;
		}
		return END;
	}
}
