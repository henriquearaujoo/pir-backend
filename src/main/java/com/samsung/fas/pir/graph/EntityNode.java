package com.samsung.fas.pir.graph;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class EntityNode {
	@Getter
	@Setter
	private				String						table;

	@Getter
	@Setter
	private				String						entity;

	@Getter
	@Setter
	private 			Map<String, Set<String>>	properties;

	@Getter
	@Setter
	private	transient	List<EntityNode>			nodes;

	@Getter
	@Setter
	private	transient	boolean						visited;

	public EntityNode(String table, String entity) {
		setTable(table);
		setEntity(entity);
		setVisited(false);
	}

	public int getSize() {
		return properties != null? properties.size() : 0;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}