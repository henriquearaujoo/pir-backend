package com.samsung.fas.pir.models.entity.user.embedded;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class Person implements Serializable {
	private static final long serialVersionUID = 2318443685752760309L;

	@Getter
	@Setter
	@Column(name="rg", length=32)
	private		String			rg;
	
	@Getter
	@Setter
	@Column(name="rg_emitter")
	private		String			emitter;
	
	@Getter
	@Setter
	@Column(name="cpf", length=11, unique=true)
	private		String			cpf;
}
