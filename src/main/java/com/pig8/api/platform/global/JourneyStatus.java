package com.pig8.api.platform.global;

public enum JourneyStatus {
	NOT_COMPLEE("待完成"),
	NOT_PUBLISH("待发布"),
	PUBLISHED("已发布"),
	OFFLINE("已下架");
	private String desc;

	JourneyStatus(String desc) {
		this.desc = desc;
	}

	public String toString() {
		return desc;
	}
	public static JourneyStatus valueOf(int value) {
		switch (value) {
			case 0:
				return NOT_COMPLEE;
			case 1:
				return NOT_PUBLISH;
			case 2:
				return PUBLISHED;
			case 3:
				return OFFLINE;
			default:
				return OFFLINE;
			}
		}
	public static JourneyStatus toValue(String value) {
		value = value.toLowerCase();
		if (value.equals(NOT_COMPLEE.name().toLowerCase())){
			return NOT_COMPLEE;
		}
		if (value.equals(NOT_PUBLISH.name().toLowerCase())){
			return NOT_PUBLISH;
		}
		if (value.equals(PUBLISHED.name().toLowerCase())){
			return PUBLISHED;
		}
		if (value.equals(OFFLINE.name().toLowerCase())){
			return OFFLINE;
		}
		return OFFLINE;
	}
}
