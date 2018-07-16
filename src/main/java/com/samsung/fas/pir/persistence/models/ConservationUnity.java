package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

@Alias("Unidade de Conservação")
@Entity
@Table(name = "conservation_unities", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"name", "regional_id"}, name = "unity_name"),
	@UniqueConstraint(columnNames = {"abbreviation"}, name = "unity_abbreviation")
})
@DynamicUpdate
@DynamicInsert
public class ConservationUnity extends Base {
	@Alias("Nome")
	@Getter
	@Setter
	@Column(columnDefinition = "CITEXT")
	private		String					name;

	@Alias("Nome")
	@Getter
	@Setter
	@Column(columnDefinition = "VARCHAR(2)")
	private		String					abbreviation;

	// region Relations
	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(name = "relation_regional"))
	private 	Regional				regional;

	@Alias("Comunidades")
	@Getter
	@Setter
	@OneToMany(mappedBy = "unity")
	private 	Collection<Community>	communities			= new ArrayList<>();

	@Alias("Cidades")
	@Getter
	@Setter
	@OneToMany(cascade = {CascadeType.MERGE})
	@JoinColumn(name = "unity_id")
	private 	Collection<City>		cities				= new ArrayList<>();
	// endregion

	@PrePersist
	@Override
	public void prePersist() {
		super.prePersist();
		setAbbreviation(getAbbreviation().toUpperCase());
	}

	@PreUpdate
	@Override
	public void preUpdate() {
		super.preUpdate();
		setAbbreviation(getAbbreviation().toUpperCase());
	}
}