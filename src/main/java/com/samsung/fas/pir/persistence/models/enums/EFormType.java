package com.samsung.fas.pir.persistence.models.enums;

public enum EFormType {
	BTYPE			("BTYPE"),
	ATYPE			("ATYPE"),
	UNDEFINED		("UNDEFINED");

	private final String val;

	EFormType(String s) {
		val = s;
	}

	public boolean equalsName(String otherName) {
		return val.equalsIgnoreCase(otherName);
	}

	public String toString() {
		return this.val;
	}

	public static EFormType parse(String s) {
		for (EFormType v : EFormType.values())
			if (v.val.equalsIgnoreCase(s))
				return v;
		return null;
	}
}
