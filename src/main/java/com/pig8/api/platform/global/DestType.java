package com.pig8.api.platform.global;

public enum DestType {
	NONE("无"),
	CONTINENT("大洲"),
	COUNTRY("国家"),
	PROVINCE("省份"),
	CITY("城市"),
	END("无");
	private String desc;

	DestType(String desc) {
		this.desc = desc;
	}

	public String toString() {
		return desc;
	}
	public static DestType valueOf(int value) {
		switch (value) {
			case 0:
				return NONE;
			case 1:
				return CONTINENT;
			case 2:
				return COUNTRY;
			case 3:
				return PROVINCE;
			case 4:
				return CITY;
			case 5:
				return END;
			default:
				return END;
			}
		}
	public static DestType toValue(String value) {
		value = value.toLowerCase();
		if (value.equals(NONE.name().toLowerCase())){
			return NONE;
		}
		if (value.equals(CONTINENT.name().toLowerCase())){
			return CONTINENT;
		}
		if (value.equals(COUNTRY.name().toLowerCase())){
			return COUNTRY;
		}
		if (value.equals(PROVINCE.name().toLowerCase())){
			return PROVINCE;
		}
		if (value.equals(CITY.name().toLowerCase())){
			return CITY;
		}
		if (value.equals(END.name().toLowerCase())){
			return END;
		}
		return END;
	}
}
