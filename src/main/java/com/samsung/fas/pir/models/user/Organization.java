package com.samsung.fas.pir.models.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Organization implements Serializable {
	@Column(name="cnpj", length=14, unique=true)
	private		String			cnpj;
	
	@Column(name="ie", unique=true)
	private		String			ie;

	public String getCnpj() {
		return cnpj;
	}

	public String getIe() {
		return ie;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}
}