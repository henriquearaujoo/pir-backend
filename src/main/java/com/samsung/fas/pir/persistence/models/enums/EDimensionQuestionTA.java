package com.samsung.fas.pir.persistence.models.enums;

public enum EDimensionQuestionTA {
	AFFECTIVE		("SOCIO_AFFECTIVE"),
	MOTOR			("MOTOR"),
	LANGUAGE		("LANGUAGE"),
	COGNITIVE		("COGNITIVE");

	private final String val;

	EDimensionQuestionTA(String s) {
		val = s;
	}

	public boolean equalsName(String otherName) {
		return val.equalsIgnoreCase(otherName);
	}

	public String toString() {
		return this.val;
	}

	public static EDimensionQuestionTA parse(String s) {
		for (EDimensionQuestionTA v : EDimensionQuestionTA.values())
			if (v.val.equalsIgnoreCase(s))
				return v;
		return null;
	}
}
