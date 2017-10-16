package com.samsung.fas.pir.models;

import java.util.Set;

import javax.persistence.*;

@Entity
public class Agent extends User {
	@Column(name="reg_id")
	private 	String 			register;
	@OneToMany(mappedBy="agent", targetEntity=Child.class, cascade = CascadeType.PERSIST)
	private 	Set<Child>		children;

	public String getRegister() {
		return register;
	}

	public Set<Child> getChildren() {
		return children;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public void setChildren(Set<Child> children) {
		this.children = children;
	}
}
