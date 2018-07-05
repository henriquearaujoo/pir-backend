package com.samsung.fas.pir.firebase;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public enum EDestination {
	GROUP 		("GROUP"),
	DEVICE 		("DEVICE"),
	UNDEFINED	("UNDEFINED");

	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
	private		String 		enumeration;

	EDestination(String value) {
		setEnumeration(value);
	}

	public boolean equals(String value) {
		return getEnumeration().equalsIgnoreCase(value);
	}

	public String getValue() {
		return getEnumeration();
	}

	public static EDestination setValue(String s) {
		return Arrays.stream(EDestination.values()).filter(item -> item.getEnumeration().equalsIgnoreCase(s)).findAny().orElse(UNDEFINED);
	}
}
