package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "pages", uniqueConstraints = @UniqueConstraint(columnNames = "title_url", name = "title"))
@DynamicUpdate
@DynamicInsert
public class Page extends BaseID {
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
