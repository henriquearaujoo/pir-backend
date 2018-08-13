package com.samsung.fas.pir.persistence.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public enum ECivilState {
	MARRIED		("MARRIED"),
	SINGLE		("SINGLE"),
	DIVORCED	("DIVORCED"),
	SEPARATED	("SEPARATED"),
	WIDOWED		("WIDOWED"),
	STABLE		("STABLE"),
	UNDEFINED	("UNDEFINED");

	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
	private		String		enumaration;

	ECivilState(String value) {
		setEnumaration(value);
	}

	public boolean equals(String value) {
		return getEnumaration().equalsIgnoreCase(value);
	}

	public String getValue() {
		return getEnumaration();
	}

	public static ECivilState setValue(String s) {
		return Arrays.stream(ECivilState.values()).filter(item -> item.getEnumaration().equalsIgnoreCase(s)).findAny().orElse(UNDEFINED);
	}
}
