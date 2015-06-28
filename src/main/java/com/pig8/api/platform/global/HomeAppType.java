package com.pig8.api.platform.global;

public enum HomeAppType {
	NONE("δ֪����"),
	OPERATION("��Ӫλ"),
	DEST("Ŀ�ĵ�"),
	TOPIC("ר��"),
	JOURNEY("�г�"),
	GUIDE("����"),
	NONE_END("δ֪����");
	private String desc;

	HomeAppType(String desc) {
		this.desc = desc;
	}

	public String toString() {
		return desc;
	}
	public static HomeAppType valueOf(int value) {
		switch (value) {
			case 0:
				return NONE;
			case 1:
				return OPERATION;
			case 2:
				return DEST;
			case 3:
				return TOPIC;
			case 4:
				return JOURNEY;
			case 5:
				return GUIDE;
			case 6:
				return NONE;
			default:
				return NONE;
			}
		}
	public static HomeAppType toValue(String value) {
		value = value.toLowerCase();
		if (value.equals(NONE.name().toLowerCase())){
			return NONE;
		}
		if (value.equals(OPERATION.name().toLowerCase())){
			return OPERATION;
		}
		if (value.equals(DEST.name().toLowerCase())){
			return DEST;
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
		if (value.equals(NONE.name().toLowerCase())){
			return NONE;
		}
		return NONE;
	}
}
