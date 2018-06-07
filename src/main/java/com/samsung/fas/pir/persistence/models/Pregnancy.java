package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "pregnancy")
@DynamicUpdate
@DynamicInsert
@Alias("Gestação")
public class Pregnancy extends BaseID {
	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	@Alias("Mãe")
	private			Mother				pregnant;

	@Getter
	@Setter
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false, nullable = false)
	@Alias("Registrado Em")
	private 		Date				registeredAt;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	@Alias("Agente")
	private 		User				agent;

	@Getter
	@Setter
	@OneToMany(mappedBy = "pregnancy", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@Alias("Visitas")
	private			Collection<Visit>	visits;
}
