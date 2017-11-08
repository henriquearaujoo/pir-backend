package com.samsung.fas.pir.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name="pages")
@Table(uniqueConstraints= {@UniqueConstraint(columnNames= {"id", "guid"})})
@DynamicUpdate
@DynamicInsert
public class Page {
	@Setter
	@Getter
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name="id", updatable=false, nullable = false, unique = true)
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	private		UUID			id;

	@Setter
	@Getter
	@Column(name="guid", updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	private 	UUID			guid;
	
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
	@OneToMany(mappedBy="page", targetEntity=Rule.class)
	private		List<Rule>		rules;
	
	@ManyToMany(mappedBy="pages", targetEntity=EndPoints.class)
	private		List<EndPoints>	endpoints;

}
