package com.pig8.api.platform.global;

public enum SelectType {
	BEGIN("开始"),
	TOPIC("专题"),
	JOURNEY("行程"),
	GUIDE("达人"),
	END("错误状态");
	private String desc;

	SelectType(String desc) {
		this.desc = desc;
	}

	public String toString() {
		return desc;
	}
	public static SelectType valueOf(int value) {
		switch (value) {
			case 0:
				return BEGIN;
			case 1:
				return TOPIC;
			case 2:
				return JOURNEY;
			case 3:
				return GUIDE;
			case 4:
				return END;
			default:
				return END;
			}
		}
	public static SelectType toValue(String value) {
		value = value.toLowerCase();
		if (value.equals(BEGIN.name().toLowerCase())){
			return BEGIN;
		}
		if (value.equals(TOPIC.name().toLowerCase())){
			return TOPIC;
		}
		if (value.equals(JOURNEY.name().toLowerCase())){
			return JOURNEY;
		}
		if (value.equals(GUIDE.name().toLowerCase())){
			return GUIDE;
		}
		if (value.equals(END.name().toLowerCase())){
			return END;
		}
		return END;
	}
}
