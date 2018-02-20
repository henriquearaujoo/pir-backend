package com.samsung.fas.pir.persistence.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "child_responsible")
@DynamicUpdate
@DynamicInsert
public class ChildResponsible extends Responsible {
	@Getter
	@Setter
	@Column(nullable = false)
	private 	String			name;

	@Getter
	@Setter
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private 	Date 			childBirth;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	private 	Community		community;
}