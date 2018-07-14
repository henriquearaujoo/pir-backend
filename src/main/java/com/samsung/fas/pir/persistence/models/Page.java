package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.Base;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "pages", uniqueConstraints = @UniqueConstraint(columnNames = "title_url", name = "title"))
@DynamicUpdate
@DynamicInsert
public class Page extends Base {
	@Setter
	@Getter
	@Column(name="title_url", nullable=false)
	private		String				title;

	@Setter
	@Getter
	@Column(name="url_path", nullable=false)
	private		String				url;
	
	@Getter
	@Setter
	@OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true, mappedBy = "page")
	private 	Collection<Rule>	rules;
}
