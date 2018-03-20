package com.samsung.fas.pir.persistence.models.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public enum EChildGender {
	FEMALE		("FEMALE"),
	MALE		("MALE"),
	UNDEFINED	("UNDEFINED");

	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
	private		String	enumaration;

	EChildGender(String value) {
		setEnumaration(value);
	}

	public boolean equals(String value) {
		return getEnumaration().equalsIgnoreCase(value);
	}

	public String getValue() {
		return getEnumaration();
	}

	public static EChildGender setValue(String s) {
		return Arrays.stream(EChildGender.values()).filter(item -> item.getEnumaration().equalsIgnoreCase(s)).findAny().orElse(UNDEFINED);
	}
}
