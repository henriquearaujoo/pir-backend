package com.samsung.fas.pir.persistence.models.entity;

import com.samsung.fas.pir.persistence.models.enums.ECivilState;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "mothers")
@DynamicUpdate
@DynamicInsert
public class Mother extends Responsible {
	@Getter
	@Setter
	@Column(nullable = false)
	private 	String			name;

	@Getter
	@Setter
	@Column(nullable = false)
	private 	long			children;

	@Getter
	@Setter
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ECivilState civilState;

	@Getter
	@Setter
	@ManyToOne(optional = false)
	@JoinColumn(name = "community_id")
	private 	Community		community;
}