package com.pig8.api.platform.global;

public enum MessageType {
	BEGIN("开始"),
	MESSAGE_TYPE_SYS("系统消息"),
	MESSAGE_TYPE_JOURNEY("行程消息"),
	MESSAGE_TYPE_ORDER("订单消息"),
	END("错误状态");
	private String desc;

	MessageType(String desc) {
		this.desc = desc;
	}

	public String toString() {
		return desc;
	}
	public static MessageType valueOf(int value) {
		switch (value) {
			case 0:
				return BEGIN;
			case 1:
				return MESSAGE_TYPE_SYS;
			case 2:
				return MESSAGE_TYPE_JOURNEY;
			case 3:
				return MESSAGE_TYPE_ORDER;
			case 4:
				return END;
			default:
				return END;
			}
		}
	public static MessageType toValue(String value) {
		value = value.toLowerCase();
		if (value.equals(BEGIN.name().toLowerCase())){
			return BEGIN;
		}
		if (value.equals(MESSAGE_TYPE_SYS.name().toLowerCase())){
			return MESSAGE_TYPE_SYS;
		}
		if (value.equals(MESSAGE_TYPE_JOURNEY.name().toLowerCase())){
			return MESSAGE_TYPE_JOURNEY;
		}
		if (value.equals(MESSAGE_TYPE_ORDER.name().toLowerCase())){
			return MESSAGE_TYPE_ORDER;
		}
		if (value.equals(END.name().toLowerCase())){
			return END;
		}
		return END;
	}
}
