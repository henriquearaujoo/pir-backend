package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "survey")
@DynamicUpdate
@DynamicInsert
@Alias("Enquete")
public class Survey extends Base {
	@Getter
	@Setter
	@Column(columnDefinition = "CITEXT")
	private		String					description;

	@Getter
	@Setter
	@OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
	private 	Collection<SQuestion> 	questions			= new ArrayList<>();
}
