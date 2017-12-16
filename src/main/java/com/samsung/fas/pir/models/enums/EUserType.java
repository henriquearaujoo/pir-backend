package com.samsung.fas.pir.models.enums;

public enum EUserType {
	PFIS("PFis"),
	PJUR("IndividualPerson"),
	UNKNOWN("Unknown");

	private final String val;

	EUserType(String s) {
		val = s;
	}

	public boolean equalsName(String otherName) {
		return val.equals(otherName);
	}

	public String toString() {
		return this.val;
	}

	public static EUserType parse(String s) {
		for(EUserType v : EUserType.values())
			if(v.val.equals(s))
				return v;
		return EUserType.UNKNOWN;
	}
}
