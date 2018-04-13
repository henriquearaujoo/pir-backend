package com.samsung.fas.pir.persistence.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public enum EMediaType {
	PICTURE2D	("PICTURE2D"),
	PICTURE360	("PICTURE360"),
	VIDEO2D		("VIDEO2D"),
	VIDEO360	("VIDEO360"),
	FILE		("FILE");

	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
	private		String		enumaration;

	EMediaType(String value) {
		setEnumaration(value);
	}

	public boolean equals(String value) {
		return getEnumaration().equalsIgnoreCase(value);
	}

	public String getValue() {
		return getEnumaration();
	}

	public static EMediaType setValue(String s) {
		return Arrays.stream(EMediaType.values()).filter(item -> item.getEnumaration().equalsIgnoreCase(s)).findAny().orElseThrow(() -> new RuntimeException("not.found"));
	}
}
