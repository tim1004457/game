package com.pig8.api.platform.global;

public enum UserType {
	USER("用户"),
	GUDIE("达人"),
	INTENT_USER("意向达人"),
	END("错误状态");
	private String desc;

	UserType(String desc) {
		this.desc = desc;
	}

	public String toString() {
		return desc;
	}
	public static UserType valueOf(int value) {
		switch (value) {
			case 0:
				return USER;
			case 1:
				return GUDIE;
			case 2:
				return INTENT_USER;
			case 3:
				return END;
			default:
				return END;
			}
		}
	public static UserType toValue(String value) {
		value = value.toLowerCase();
		if (value.equals(USER.name().toLowerCase())){
			return USER;
		}
		if (value.equals(GUDIE.name().toLowerCase())){
			return GUDIE;
		}
		if (value.equals(INTENT_USER.name().toLowerCase())){
			return INTENT_USER;
		}
		if (value.equals(END.name().toLowerCase())){
			return END;
		}
		return END;
	}
}
