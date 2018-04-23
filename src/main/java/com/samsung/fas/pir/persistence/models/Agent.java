package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.Visit;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "agent")
@DynamicUpdate
@DynamicInsert
public class Agent extends User {
	@Getter
	@Setter
	@Column
	private 	double					latitude;

	@Getter
	@Setter
	@Column
	private 	double					longitude;

	@Getter
	@Setter
	@OneToMany(mappedBy = "agent")
	private		Collection<Visit>		visits;
}
