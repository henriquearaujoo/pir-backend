package com.samsung.fas.pir.persistence.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public enum EGender {
	FEMALE		("FEMALE"),
	MALE		("MALE"),
	UNDEFINED	("UNDEFINED");

	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
	private		String 		enumeration;

	EGender(String value) {
		setEnumeration(value);
	}

	public boolean equals(String value) {
		return getEnumeration().equalsIgnoreCase(value);
	}

	public String getValue() {
		return getEnumeration();
	}

	public static EGender setValue(String s) {
		return Arrays.stream(EGender.values()).filter(item -> item.getEnumeration().equalsIgnoreCase(s)).findAny().orElse(UNDEFINED);
	}
}
