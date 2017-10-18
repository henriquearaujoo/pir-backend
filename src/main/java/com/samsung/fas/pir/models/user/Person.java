package com.samsung.fas.pir.models.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Person implements Serializable {
	@Column(name="rg", length=10, unique=true)
	private		String			rg;
	
	@Column(name="cpf", length=11, unique=true)
	private		String			cpf;

	public String getRg() {
		return rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
