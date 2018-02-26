package com.samsung.fas.pir.persistence.models.enums;

public enum ECivilState {
	MARRIED		("MARRIED"),
	SINGLE		("SINGLE"),
	DIVORCED	("DIVORCED"),
	SEPARATED	("SEPARATED"),
	WIDOWED		("WIDOWED"),
	UNSPECIFIED	("UNSPECIFIED");

	private final String val;

	ECivilState(String s) {
		val = s;
	}

	public boolean equalsName(String otherName) {
		return val.equalsIgnoreCase(otherName);
	}

	public String toString() {
		return this.val;
	}

	public static ECivilState parse(String s) {
		for (ECivilState v : ECivilState.values())
			if (v.val.equalsIgnoreCase(s))
				return v;
		return UNSPECIFIED;
	}
}
