package com.pig8.api.platform.global;

public enum HomeBannerType {
	DEST("Ŀ�ĵ�"),
	TOPIC("ר��"),
	GUIDE("����"),
	JOURNEY("�г�"),
	END("����״̬");
	private String desc;

	HomeBannerType(String desc) {
		this.desc = desc;
	}

	public String toString() {
		return desc;
	}
	public static HomeBannerType valueOf(int value) {
		switch (value) {
			case 0:
				return DEST;
			case 1:
				return TOPIC;
			case 2:
				return GUIDE;
			case 3:
				return JOURNEY;
			case 4:
				return END;
			default:
				return END;
			}
		}
	public static HomeBannerType toValue(String value) {
		value = value.toLowerCase();
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
		if (value.equals(END.name().toLowerCase())){
			return END;
		}
		return END;
	}
}
