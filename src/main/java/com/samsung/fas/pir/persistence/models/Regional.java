package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "regional", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}, name = "regional_name"))
@DynamicUpdate
@DynamicInsert
@Alias("Regional")
public class Regional extends Base {
	@Alias("Nome")
	@Getter
	@Setter
	@Column(columnDefinition = "CITEXT", nullable = false)
	private		String								name;

	@Alias("Unidades de Conservação")
	@Getter
	@Setter
	@OneToMany(mappedBy = "regional", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private 	Collection<ConservationUnity>		unities			= new ArrayList<>();
}