package com.samsung.fas.pir.models;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity(name="pages")
@DynamicUpdate
@DynamicInsert
public class Page {
	@Setter
	@Getter
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="id", updatable=false)
	private		UUID			id;
	
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
	private		List<Page>		pages;

}
