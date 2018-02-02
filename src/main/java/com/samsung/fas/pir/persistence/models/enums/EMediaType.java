package com.samsung.fas.pir.persistence.models.enums;

import javax.ws.rs.NotFoundException;

public enum EMediaType {
	PICTURE2D	("PICTURE2D"),
	PICTURE360	("PICTURE360"),
	VIDEO2D		("VIDEO2D"),
	VIDEO360	("VIDEO360"),
	FILE		("FILE");

	private String val;

	EMediaType(String val) {
		this.val = val;
	}
	public String getVal() {
		return val;
	}
	public static EMediaType parse(String val) {
		for(EMediaType f : EMediaType.values())
			if(f.getVal().equalsIgnoreCase(val))
				return f;
		throw new NotFoundException();
	}
}
