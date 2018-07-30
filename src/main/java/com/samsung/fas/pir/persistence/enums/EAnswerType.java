package com.samsung.fas.pir.persistence.enums;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public enum EAnswerType {
	OBJECTIVE		("OBJECTIVE"),
	SUBJECTIVE		("SUBJECTIVE"),
	DISSERTATION	("DISSERTATION"),
	UNDEFINED		("UNDEFINED");

	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
	private		String		enumeration;

	EAnswerType(String value) {
		setEnumeration(value);
	}

	public boolean equals(String value) {
		return getEnumeration().equalsIgnoreCase(value);
	}

	public String getValue() {
		return getEnumeration();
	}

	public static EAnswerType setValue(String s) {
		return Arrays.stream(EAnswerType.values()).filter(item -> item.getEnumeration().equalsIgnoreCase(s)).findAny().orElse(UNDEFINED);
	}
}