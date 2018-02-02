package com.samsung.fas.pir.persistence.models.enums;

public enum EUserType {
	PFIS("PFIS"),
	PJUR("PJUR"),
	UNKNOWN("UNKNOWN");

	private final String val;

	EUserType(String s) {
		val = s;
	}

	public boolean equals(String otherName) {
		return val.equalsIgnoreCase(otherName);
	}

	public String toString() {
		return this.val;
	}

	public static EUserType parse(String s) {
		for(EUserType v : EUserType.values())
			if(v.val.equalsIgnoreCase(s))
				return v;
		return EUserType.UNKNOWN;
	}
}
