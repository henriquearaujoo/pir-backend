package com.samsung.fas.pir.models.entity.user.embedded;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

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

	@Getter
	@Setter
	@Column(name="fantasy_name")
	private		String			fantasyName;

	@Getter
	@Setter
	@Column(name="social_name")
	private		String			socialName;
}