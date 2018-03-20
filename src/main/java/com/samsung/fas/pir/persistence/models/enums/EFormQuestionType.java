package com.samsung.fas.pir.persistence.models.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public enum EFormQuestionType {
	AFFECTIVE		("SOCIO_AFFECTIVE"),
	MOTOR			("MOTOR"),
	LANGUAGE		("LANGUAGE"),
	COGNITIVE		("COGNITIVE"),
	NEURO			("NEURO"),
	UNDEFINED		("UNDEFINED");

	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
	private		String	enumaration;

	EFormQuestionType(String value) {
		setEnumaration(value);
	}

	public boolean equals(String value) {
		return getEnumaration().equalsIgnoreCase(value);
	}

	public String getValue() {
		return getEnumaration();
	}

	public static EFormQuestionType setValue(String s) {
		return Arrays.stream(EFormQuestionType.values()).filter(item -> item.getEnumaration().equalsIgnoreCase(s)).findAny().orElse(UNDEFINED);
	}
}
