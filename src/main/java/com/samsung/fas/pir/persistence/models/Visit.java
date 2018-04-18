package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "visits")
@DynamicUpdate
@DynamicInsert
public class Visit extends BaseID {
	@Getter
	@Setter
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false, nullable = false)
	private 	Date			doneAt;

	@Getter
	@Setter
	@Column
	private 	int				number;

	@Getter
	@Setter
	@Column
	private 	short			familyRating;

	@Getter
	@Setter
	@Column
	private 	short			agentRating;

	@Getter
	@Setter
	@Column
	private 	long			duration;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	private 	User			agent;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn
	private 	Mother			mother;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn
	private 	Child			child;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	private 	Chapter			chapter;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn
	private 	Form			form;
}
