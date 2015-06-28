package com.pig8.api.platform.global;

public enum IdentifyType {
	BEGIN("开始"),
	ID_CARD("身份证"),
	PASSPORT("护照"),
	END("未知类型");
	private String desc;

	IdentifyType(String desc) {
		this.desc = desc;
	}

	public String toString() {
		return desc;
	}
	public static IdentifyType valueOf(int value) {
		switch (value) {
			case 0:
				return BEGIN;
			case 1:
				return ID_CARD;
			case 2:
				return PASSPORT;
			case 3:
				return END;
			default:
				return END;
			}
		}
	public static IdentifyType toValue(String value) {
		value = value.toLowerCase();
		if (value.equals(BEGIN.name().toLowerCase())){
			return BEGIN;
		}
		if (value.equals(ID_CARD.name().toLowerCase())){
			return ID_CARD;
		}
		if (value.equals(PASSPORT.name().toLowerCase())){
			return PASSPORT;
		}
		if (value.equals(END.name().toLowerCase())){
			return END;
		}
		return END;
	}
}
