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
@Table(name = "congenital_deficiencies", uniqueConstraints = @UniqueConstraint(name = "deficiency_name", columnNames = {"name"}))
@DynamicUpdate
@DynamicInsert
@Alias("Deficiências Congênitas")
public class CongenitalDeficiency extends Base {
	@Getter
	@Setter
	@Column(columnDefinition = "CITEXT")
	private 		String						name;

	// region Relations
	@Getter
	@Setter
	@ManyToMany
	private 		Collection<Child> 			children;
	// endregion
}
