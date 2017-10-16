package com.samsung.fas.pir.models;

import java.util.Set;

import javax.persistence.*;

@Entity
public class Agent {
	@Id
	@GeneratedValue
	private		long 			id;
	@Column(name="name")
	private 	String 			name;
	@Column(name="reg_id")
	private 	String 			register;
	// In this example, we will insert any data associated in cascade mode
	@OneToMany(mappedBy="agent", targetEntity=Child.class, cascade = CascadeType.PERSIST)
	private 	Set<Child>		children;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRegister() {
		return register;
	}

	public Set<Child> getChildren() {
		return children;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public void setChildren(Set<Child> children) {
		this.children = children;
	}
}
