package com.samsung.fas.pir.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity(name="city")
public class City {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id")
	private		long		id;
	
	@Column(name="name")
	@NotNull(message = "Invalid value for name field (NULL)")
	@NotEmpty(message = "Invalid value for name field (EMPTY)")
	private		String		name;
	
	@ManyToOne
	@JoinColumn(name="state_id_fk", nullable=false)
	@NotNull(message = "Invalid value for state field (NULL)")
	@NotEmpty(message = "Invalid value for state field (EMPTY)")
	private		State		state;
	
	@OneToMany(mappedBy="address.city", targetEntity=User.class)
	private		List<User>		user;
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public State getState() {
		return state;
	}
	
	public List<User> getUser() {
		return user;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public void setUser(List<User> user) {
		this.user = user;
	}
}
