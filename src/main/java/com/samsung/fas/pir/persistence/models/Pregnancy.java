package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
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
	@Column(name = "mobile_id")
	private		long					mobileId;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn(name = "mother_id")
	@Alias("Mãe")
	private			Responsible			pregnant;

	@Getter
	@Setter
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false, nullable = false)
	@Alias("Registrado Em")
	private 		Date				registeredAt;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn
	@Alias("Agente")
	private 		User				agent;

	@Getter
	@Setter
	@OneToMany(mappedBy = "pregnancy", cascade = CascadeType.ALL)
	@Alias("Filhos")
	private 		Collection<SAnswer>	answers				= new ArrayList<>();

	@Getter
	@Setter
	@OneToMany(mappedBy = "pregnancy", cascade = CascadeType.ALL)
	@Alias("Visitas")
	private			Collection<Visit>	visits				= new ArrayList<>();
}
