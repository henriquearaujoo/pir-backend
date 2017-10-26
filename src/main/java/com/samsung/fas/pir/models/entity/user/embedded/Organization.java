package com.samsung.fas.pir.models.entity.user.embedded;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class Organization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@Column(name="cnpj", length=14, unique=true)
	private		String			cnpj;
	
	@Getter
	@Setter
	@Column(name="ie", length=32)
	private		String			ie;
}