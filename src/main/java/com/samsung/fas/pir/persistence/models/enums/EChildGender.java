package com.samsung.fas.pir.persistence.models.enums;

public enum EChildGender {
	FEMALE		("FEMALE"),
	MALE		("MALE"),
	UNSPECIFIED	("UNSPECIFIED");

	private final String val;

	EChildGender(String s) {
		val = s;
	}

	public boolean equalsName(String otherName) {
		return val.equalsIgnoreCase(otherName);
	}

	public String toString() {
		return this.val;
	}

	public static EChildGender parse(String s) {
		for (EChildGender v : EChildGender.values())
			if (v.val.equalsIgnoreCase(s))
				return v;
		return UNSPECIFIED;
	}
}
