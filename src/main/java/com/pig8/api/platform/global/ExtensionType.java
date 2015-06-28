package com.pig8.api.platform.global;

public enum ExtensionType {
	PC("PC��"),
	WECHAT("΢�Ű�"),
	INTENT_GUIDE("������ļ��"),
	END("�����");
	private String desc;

	ExtensionType(String desc) {
		this.desc = desc;
	}

	public String toString() {
		return desc;
	}
	public static ExtensionType valueOf(int value) {
		switch (value) {
			case 0:
				return PC;
			case 1:
				return WECHAT;
			case 2:
				return INTENT_GUIDE;
			case 3:
				return END;
			default:
				return END;
			}
		}
	public static ExtensionType toValue(String value) {
		value = value.toLowerCase();
		if (value.equals(PC.name().toLowerCase())){
			return PC;
		}
		if (value.equals(WECHAT.name().toLowerCase())){
			return WECHAT;
		}
		if (value.equals(INTENT_GUIDE.name().toLowerCase())){
			return INTENT_GUIDE;
		}
		if (value.equals(END.name().toLowerCase())){
			return END;
		}
		return END;
	}
}
