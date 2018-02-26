package com.samsung.fas.pir.persistence.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "pages")
@DynamicUpdate
@DynamicInsert
public class Page {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private		long			id;

	@Getter
	@Setter
	@Column(insertable = false, updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	@Generated(GenerationTime.INSERT)
	private 	UUID 			uuid;
	
	@Setter
	@Getter
	@Column(name="title_url", nullable=false, unique=true)
	private		String			title;

	@Setter
	@Getter
	@Column(name="url_path", nullable=false)
	private		String			url;
	
//	@Setter
//	@Getter
//	@Column(name="created_by", updatable=false, nullable=false)
//	private		User			whoCreated;
//	
//	@Setter
//	@Getter
//	@Column(name="updated_by", nullable=false)
//	private		User			whoUpdated;
	
	@Getter
	@Setter
	@OneToMany
	@JoinColumn(name = "page_id")
	private 	Set<Rule> 		rules;
}
