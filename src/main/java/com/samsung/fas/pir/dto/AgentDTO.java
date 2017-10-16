package com.samsung.fas.pir.dto;

import java.util.Set;
import java.util.UUID;

import com.samsung.fas.pir.models.Agent;
import com.samsung.fas.pir.models.Child;

/*
 * This is an example class (Data Transfer Object)
 */
public class AgentDTO {
	private		UUID 		id;
	private 	String 		name;
	private 	String 		register;
	private 	Set<Child>	children;
	
	public AgentDTO() {
		super();
	}
	
	public AgentDTO(Agent agent) {
		id			= agent.getId();
		name		= agent.getName();
		register	= agent.getRegister();
		children	= agent.getChildren();
	}

	public UUID getId() {
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
	
	public Agent getModel() {
		Agent model = new Agent();
		model.setName(name);
		model.setRegister(register);
		children.forEach((child) -> child.setAgent(model));
		model.setChildren(children);
		return model;
	}
}
