package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.BaseNID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

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
	private 	User			user;

	@Getter
	@Setter
	@Column(name="cnpj", length=14)
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