package com.samsung.fas.pir.persistence.models.enums;

public enum  EQuestionType {
	OBJECTIVE("OBJECTIVE"),
	SUBJECTIVE("SUBJECTIVE"),
	DISSERTATIVE("DISSERTATIVE"),
	UNKNOWN("UNKNOWN");

	private final String val;

	EQuestionType(String s) {
		val = s;
	}

	public boolean equalsName(String otherName) {
		return val.equalsIgnoreCase(otherName);
	}

	public String toString() {
		return this.val;
	}

	public static EQuestionType parse(String s) {
		for (EQuestionType v : EQuestionType.values())
			if (v.val.equalsIgnoreCase(s))
				return v;
		return EQuestionType.UNKNOWN;
	}
}