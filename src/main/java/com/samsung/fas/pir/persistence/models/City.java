package com.samsung.fas.pir.persistence.models;

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
public class City {
	@Getter
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id", updatable=false)
	private		long		id;

	@Getter
	@Setter
	@Column(insertable = false, updatable=false, nullable = false, unique = true, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	@Generated(GenerationTime.INSERT)
	private 	UUID 		uuid;
	
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
