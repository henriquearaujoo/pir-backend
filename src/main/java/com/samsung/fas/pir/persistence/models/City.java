package com.samsung.fas.pir.persistence.models;

import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cities")
public class City extends BaseID {
	@Getter
	@Setter
	@Column(name="name", nullable=false)
	private		String		name;
	
	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name="state_id_fk", nullable=false)
	private		State		state;
	
	@Getter
	@Setter
	@OneToMany(mappedBy="city", targetEntity=Address.class)
	private		List<User>		user;
}
