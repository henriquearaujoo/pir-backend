package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.BaseNID;
import com.samsung.fas.pir.persistence.annotations.Alias;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "entity", uniqueConstraints = @UniqueConstraint(columnNames = "cnpj", name = "cnpj"))
@DynamicUpdate
@DynamicInsert
public class LegalEntity extends BaseNID {
	@Getter
	@Setter
	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "id")
	@Alias("Usuário")
	private 	User			user;

	@Getter
	@Setter
	@Column(name="cnpj", length=14)
	@Alias("CNPJ")
	private		String			cnpj;
	
	@Getter
	@Setter
	@Column(name="ie", length=32)
	@Alias("Inscrição Estadual")
	private		String			ie;

	@Getter
	@Setter
	@Column(name="fantasy_name")
	@Alias("Nome Fantasia")
	private		String			fantasyName;

	@Getter
	@Setter
	@Column(name="social_name")
	@Alias("Razão Social")
	private		String			socialName;
}