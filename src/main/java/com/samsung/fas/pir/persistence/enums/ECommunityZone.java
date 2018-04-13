package com.samsung.fas.pir.persistence.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public enum ECommunityZone {
	RURAL		("RURAL"),
	URBAN		("URBAN"),
	UNDEFINED	("UNDEFINED");

	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
	private		String		enumaration;

	ECommunityZone(String value) {
		setEnumaration(value);
	}

	public boolean equals(String value) {
		return getEnumaration().equalsIgnoreCase(value);
	}

	public String getValue() {
		return getEnumaration();
	}

	public static ECommunityZone setValue(String s) {
		return Arrays.stream(ECommunityZone.values()).filter(item -> item.getEnumaration().equalsIgnoreCase(s)).findAny().orElse(UNDEFINED);
	}
}
