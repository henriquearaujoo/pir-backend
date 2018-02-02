package com.samsung.fas.pir.login.persistence.models.enums;

public enum EAudience {
	WEB 	("WEB"),
	MOBILE 	("MOBILE"),
	UNKNOWN	("UNKNOWN");

	private final String val;

	EAudience(String s) {
		val = s;
	}

	public boolean equals(String otherName) {
		return val.equalsIgnoreCase(otherName);
	}

	public String toString() {
		return this.val;
	}

	public static EAudience parse(String s) {
		for(EAudience v : EAudience.values())
			if(v.val.equals(s))
				return v;
		return EAudience.UNKNOWN;
	}
}
