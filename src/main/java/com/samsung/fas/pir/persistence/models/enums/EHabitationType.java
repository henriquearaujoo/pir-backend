package com.samsung.fas.pir.persistence.models.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public enum EHabitationType {
	OWNED		("OWNED"),
	LEASED		("LEASED"),
	CEDED		("CEDED"),
	UNDEFINED	("UNDEFINED");

	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
	private		String	enumaration;

	EHabitationType(String value) {
		setEnumaration(value);
	}

	public boolean equals(String value) {
		return getEnumaration().equalsIgnoreCase(value);
	}

	public String getValue() {
		return getEnumaration();
	}

	public static EHabitationType setValue(String s) {
		return Arrays.stream(EHabitationType.values()).filter(item -> item.getEnumaration().equalsIgnoreCase(s)).findAny().orElse(UNDEFINED);
	}
}
