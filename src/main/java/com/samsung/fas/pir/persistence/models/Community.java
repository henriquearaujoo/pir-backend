package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.enums.ECommunityZone;
import com.samsung.fas.pir.persistence.models.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "community", uniqueConstraints = {@UniqueConstraint(name = "community_name", columnNames = {"name", "city_id", "unity_id"})})
@DynamicUpdate
@DynamicInsert
@Alias("Comunidade")
public class Community extends Base {
	@Getter
	@Setter
	@Column(columnDefinition = "CITEXT")
	@Alias("Nome")
	private 	String						name;

	@Getter
	@Setter
	@Column
	@Enumerated(EnumType.STRING)
	@Alias("Zona")
	private 	ECommunityZone				communityZone;

	@Getter
	@Setter
	@Column
	@Alias("Suprimento de Água")
	private 	String						waterSupply;

	@Getter
	@Setter
	@Column
	@Alias("Destino do Lixo")
	private 	String						garbageDestination;

	@Getter
	@Setter
	@Column
	@Alias("Vias de Acesso")
	private 	String						access;

	@Getter
	@Setter
	@Column
	@Alias("Serviços de Saúde")
	private 	String						healthServices;

	@Getter
	@Setter
	@Column(name = "income")
	@Alias("Fone de Receita")
	private 	String						mainIncome;

	@Getter
	@Setter
	@Column
	@Alias("Produções Culturais")
	private 	String						culturalProductions;

	@Getter
	@Setter
	@Column
	@Alias("Possui Jardim de Infância")
	private 	boolean						kindergarten;

	@Getter
	@Setter
	@Column
	@Alias("Possui Ensino Fundamental")
	private 	boolean						elementarySchool;

	@Getter
	@Setter
	@Column
	@Alias("Possui Ensino Médio")
	private 	boolean						highSchool;

	@Getter
	@Setter
	@Column
	@Alias("Possui Ensino Superior")
	private 	boolean						college;

	@Getter
	@Setter
	@Column
	@Alias("Possui Eletricidade")
	private 	boolean 					electricity;

	@Getter
	@Setter
	@Column
	@Alias("Possui Centro Cívico")
	private 	boolean						communityCenter;

	@Getter
	@Setter
	@Column
	@Alias("Possui Centro Religioso")
	private 	boolean						religiousPlace;

	@Getter
	@Setter
	@Column
	@Alias("Possui Eventos Culturais")
	private 	boolean						culturalEvents;

	@Getter
	@Setter
	@Column
	@Alias("Possui Patrono")
	private 	boolean						patron;

	@Getter
	@Setter
	@Column
	@Alias("Possui Líderes Comunitários")
	private 	boolean						communityLeaders;

	@Getter
	@Setter
	@Column
	@Alias("Latitude")
	private 	Double						latitude;

	@Getter
	@Setter
	@Column
	@Alias("Longitude")
	private 	Double						longitude;

	@Alias("UC")
	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(name = "relation_unity"))
	private 	ConservationUnity			unity;

	@Alias("Município")
	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(name = "relation_city"))
	private 	City						city;

	@Getter
	@Setter
	@OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
	@Alias("Responsáveis")
	private 	Collection<Family>			family				= new ArrayList<>();
}