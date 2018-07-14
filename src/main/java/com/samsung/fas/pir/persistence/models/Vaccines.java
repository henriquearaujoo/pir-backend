package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.persistence.models.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "vaccines", uniqueConstraints = @UniqueConstraint(name = "vaccine_name", columnNames = {"name"}))
@DynamicUpdate
@DynamicInsert
@Alias("Vacinas")
public class Vaccines extends Base {
	@Getter
	@Setter
	@Column(columnDefinition = "CITEXT")
	private 		String						name;

	// region Relations
	@Getter
	@Setter
	@ManyToMany
	private 		Collection<Pregnancy>		pregnancies;
	// endregion
}
