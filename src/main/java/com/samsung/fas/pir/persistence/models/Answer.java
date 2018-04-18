package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "answers", uniqueConstraints = @UniqueConstraint(columnNames = {"child_id", "alternative_id", "responsible_id"}, name = "answer"))
@DynamicUpdate
@DynamicInsert
public class Answer extends BaseID {
	@Getter
	@Setter
	@Column
	private 	String			description;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn
	private		Child			child;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn
	private		Responsible		responsible;

	@Getter
	@Setter
	@ManyToOne
	@JoinColumn
	private 	Alternative		alternative;
}