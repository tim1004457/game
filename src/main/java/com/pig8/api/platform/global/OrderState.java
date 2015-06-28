package com.pig8.api.platform.global;

public enum OrderState {
	BEGIN("开始"),
	CONFIRMING("待确认"),
	PAYING("待付款"),
	SUBCRIBED("已预定"),
	TRAVELING("旅行中"),
	FINISH("完成"),
	CANCEL("取消"),
	REFUSE("拒绝"),
	UNSUBCRIBED("退订"),
	UNSUBCRIBING("退订中"),
	END("错误状态");
	private String desc;

	OrderState(String desc) {
		this.desc = desc;
	}

	public String toString() {
		return desc;
	}
	public static OrderState valueOf(int value) {
		switch (value) {
			case 0:
				return BEGIN;
			case 1:
				return CONFIRMING;
			case 2:
				return PAYING;
			case 3:
				return SUBCRIBED;
			case 4:
				return TRAVELING;
			case 5:
				return FINISH;
			case 6:
				return CANCEL;
			case 7:
				return REFUSE;
			case 8:
				return UNSUBCRIBED;
			case 9:
				return UNSUBCRIBING;
			case 10:
				return END;
			default:
				return END;
			}
		}
	public static OrderState toValue(String value) {
		value = value.toLowerCase();
		if (value.equals(BEGIN.name().toLowerCase())){
			return BEGIN;
		}
		if (value.equals(CONFIRMING.name().toLowerCase())){
			return CONFIRMING;
		}
		if (value.equals(PAYING.name().toLowerCase())){
			return PAYING;
		}
		if (value.equals(SUBCRIBED.name().toLowerCase())){
			return SUBCRIBED;
		}
		if (value.equals(TRAVELING.name().toLowerCase())){
			return TRAVELING;
		}
		if (value.equals(FINISH.name().toLowerCase())){
			return FINISH;
		}
		if (value.equals(CANCEL.name().toLowerCase())){
			return CANCEL;
		}
		if (value.equals(REFUSE.name().toLowerCase())){
			return REFUSE;
		}
		if (value.equals(UNSUBCRIBED.name().toLowerCase())){
			return UNSUBCRIBED;
		}
		if (value.equals(UNSUBCRIBING.name().toLowerCase())){
			return UNSUBCRIBING;
		}
		if (value.equals(END.name().toLowerCase())){
			return END;
		}
		return END;
	}
}
