package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.enums.ECommunityZone;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "community", uniqueConstraints = {
	@UniqueConstraint(name = "community_name", columnNames = {"name", "city_id"})
})
@DynamicUpdate
@DynamicInsert
@Alias("Comunidade")
public class Community extends BaseID {
	@Getter
	@Setter
	@Transient
	private		long						mobileId;

	@Getter
	@Setter
	@Column
	@Alias("UC")
	private 	String						uc;

	@Getter
	@Setter
	@Column
	@Alias("Regional")
	private 	String						regional;

	@Getter
	@Setter
	@Column(nullable = false, columnDefinition = "CITEXT")
	@Alias("Nome")
	private 	String						name;

	@Getter
	@Setter
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@Alias("Zona")
	private 	ECommunityZone				communityZone;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Suprimento de Água")
	private 	String						waterSupply;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Destino do Lixo")
	private 	String						garbageDestination;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Vias de Acesso")
	private 	String						access;

	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Serviços de Saúde")
	private 	String						healthServices;

	@Getter
	@Setter
	@Column(name = "income", nullable = false)
	@Alias("Fone de Receita")
	private 	String						mainIncome;

	@Getter
	@Setter
	@Column
	@Alias("Produções Culturais")
	private 	String						culturalProductions;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Possui Jardim de Infância")
	private 	boolean						hasKindergarten;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Possui Ensino Fundamental")
	private 	boolean						hasElementarySchool;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Possui Ensino Médio")
	private 	boolean						hasHighSchool;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Possui Ensino Superior")
	private 	boolean						hasCollege;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Possui Eletricidade")
	private 	boolean 					hasElectricity;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Possui Centro Cívico")
	private 	boolean						hasCommunityCenter;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Possui Centro Religioso")
	private 	boolean						hasReligiousPlace;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Possui Eventos Culturais")
	private 	boolean						hasCulturalEvents;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Possui Patrono")
	private 	boolean						hasPatron;

	@Accessors(fluent = true)
	@Getter
	@Setter
	@Column(nullable = false)
	@Alias("Possui Líderes Comunitários")
	private 	boolean						hasCommunityLeaders;

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

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn(name = "city_id")
	@Alias("Município")
	private 	City						city;

	@Getter
	@Setter
	@OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
	@Alias("Responsáveis")
	private 	Collection<Responsible>		responsible			= new ArrayList<>();
}