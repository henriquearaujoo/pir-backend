package com.samsung.fas.pir.persistence.models.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public enum EFormType {
	BTYPE			("BTYPE"),
	ATYPE			("ATYPE"),
	UNDEFINED		("UNDEFINED");

	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
	private		String	enumaration;

	EFormType(String value) {
		setEnumaration(value);
	}

	public boolean equals(String value) {
		return getEnumaration().equalsIgnoreCase(value);
	}

	public String getValue() {
		return getEnumaration();
	}

	public static EFormType setValue(String s) {
		return Arrays.stream(EFormType.values()).filter(item -> item.getEnumaration().equalsIgnoreCase(s)).findAny().orElse(UNDEFINED);
	}
}
