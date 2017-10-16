package com.samsung.fas.pir.models;

import java.util.Date;

import javax.persistence.*;

@Entity
public class Child {
	@Id
	@GeneratedValue
	private 	long		id;
	@Column(name="name")
	private 	String 		name;
	@Column(name="age")
	private 	int 		age;
	@Column(name="birth")
	private 	Date 		birth;
	@Column(name="parent")
	private 	String		relatives;
	// The foreign key in this example must contain a value
	@ManyToOne
	@JoinColumn(name="agent_id_fk", nullable=false)
	private		Agent		agent;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public Date getBirth() {
		return birth;
	}

	public String getRelatives() {
		return relatives;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public void setRelatives(String relatives) {
		this.relatives = relatives;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}
}
