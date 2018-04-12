package com.samsung.fas.pir.persistence.models;

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
public class Page {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private		long				id;

	@Getter
	@Setter
	@Column(insertable = false, updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	@Generated(GenerationTime.INSERT)
	private 	UUID 				uuid;
	
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
