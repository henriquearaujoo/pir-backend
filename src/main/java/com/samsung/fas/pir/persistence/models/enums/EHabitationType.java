package com.samsung.fas.pir.persistence.models.enums;

public enum EHabitationType {
	OWNED		("OWNED"),
	LEASED		("LEASED"),
	CEDED		("CEDED"),
	UNSPECIFIED	("UNSPECIFIED");

	private final String val;

	EHabitationType(String s) {
		val = s;
	}

	public boolean equalsName(String otherName) {
		return val.equalsIgnoreCase(otherName);
	}

	public String toString() {
		return this.val;
	}

	public static EHabitationType parse(String s) {
		for (EHabitationType v : EHabitationType.values())
			if (v.val.equalsIgnoreCase(s))
				return v;
		return UNSPECIFIED;
	}
}
