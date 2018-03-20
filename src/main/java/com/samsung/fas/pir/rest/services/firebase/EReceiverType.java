package com.samsung.fas.pir.rest.services.firebase;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public enum EReceiverType {
	CONDITIONAL	("condition"),
	UNIQUE		("to");

	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
	private		String	enumaration;

	EReceiverType(String value) {
		setEnumaration(value);
	}

	public boolean equals(String value) {
		return getEnumaration().equalsIgnoreCase(value);
	}

	public String getValue() {
		return getEnumaration();
	}

	public static EReceiverType setValue(String s) {
		return Arrays.stream(EReceiverType.values()).filter(item -> item.getEnumaration().equalsIgnoreCase(s)).findAny().orElse(UNIQUE);
	}
}
