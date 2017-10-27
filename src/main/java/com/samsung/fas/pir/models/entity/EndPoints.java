package com.samsung.fas.pir.models.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

@Entity(name="endpoints")
@DynamicUpdate
@DynamicInsert
public class EndPoints {
	@Getter
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id", updatable=false)
	private		long		id;
	
	@Setter
	@Getter
	@Column(name="endpoint_path", nullable=false)
	private		String			url;
	
	@ManyToMany
	@JoinTable
	private		List<Page>		pages;
}
