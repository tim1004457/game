package com.pig8.api.platform.global;

public enum OperationType {
	BEGIN("开始"),
	DEST("目的地"),
	TOPIC("主题"),
	GUIDE("达人"),
	JOURNEY("行程"),
	HTTP_URL("web跳转"),
	END("错误状态");
	private String desc;

	OperationType(String desc) {
		this.desc = desc;
	}

	public String toString() {
		return desc;
	}
	public static OperationType valueOf(int value) {
		switch (value) {
			case 0:
				return BEGIN;
			case 1:
				return DEST;
			case 2:
				return TOPIC;
			case 3:
				return GUIDE;
			case 4:
				return JOURNEY;
			case 5:
				return HTTP_URL;
			case 6:
				return END;
			default:
				return END;
			}
		}
	public static OperationType toValue(String value) {
		value = value.toLowerCase();
		if (value.equals(BEGIN.name().toLowerCase())){
			return BEGIN;
		}
		if (value.equals(DEST.name().toLowerCase())){
			return DEST;
		}
		if (value.equals(TOPIC.name().toLowerCase())){
			return TOPIC;
		}
		if (value.equals(GUIDE.name().toLowerCase())){
			return GUIDE;
		}
		if (value.equals(JOURNEY.name().toLowerCase())){
			return JOURNEY;
		}
		if (value.equals(HTTP_URL.name().toLowerCase())){
			return HTTP_URL;
		}
		if (value.equals(END.name().toLowerCase())){
			return END;
		}
		return END;
	}
}
