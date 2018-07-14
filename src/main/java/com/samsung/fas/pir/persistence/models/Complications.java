package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.Pregnancy;
import com.samsung.fas.pir.persistence.models.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "pregnancy_complications")
@DynamicUpdate
@DynamicInsert
@Alias("Complicações Gravidez")
public class Complications extends Base {
	@Getter
	@Setter
	@Column
	private 		String						description;

	// region Relations
	@Getter
	@Setter
	@ManyToMany
	private 		Collection<Pregnancy>		pregnancies;
	// endregion
}
