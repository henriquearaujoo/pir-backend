package com.samsung.fas.pir.persistence.models.enums;

public enum ECommunityZone {
	RURAL		("RURAL"),
	URBAN		("URBAN"),
	UNSPECIFIED	("UNSPECIFIED");

	private final String val;

	ECommunityZone(String s) {
		val = s;
	}

	public boolean equalsName(String otherName) {
		return val.equalsIgnoreCase(otherName);
	}

	public String toString() {
		return this.val;
	}

	public static ECommunityZone parse(String s) {
		for (ECommunityZone v : ECommunityZone.values())
			if (v.val.equalsIgnoreCase(s))
				return v;
		return UNSPECIFIED;
	}
}
