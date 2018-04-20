package com.samsung.fas.pir.rest.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class Test {
	private 	List<Node>		nodes	= new ArrayList<>();

	public Test() {
		setupGraph();
	}

	private List<Node> setupGraph() {
		Set<Class<?>> 	classes = new Reflections("com.samsung.fas.pir").getTypesAnnotatedWith(Table.class);
		List<Node>		graph	= classes.stream().map(item -> new Node(item.getAnnotation(Table.class).name(), item.getSimpleName())).collect(Collectors.toList());

		graph.forEach(node -> {
			Class<?>	clazz	= classes.stream().filter(item -> item.getSimpleName().equalsIgnoreCase(node.getEntity())).findAny().orElse(null);
			node.setProperties(clazz != null? Arrays.stream(ArrayUtils.addAll(setupFields(clazz))).collect(Collectors.groupingBy(this::getTypeName, Collectors.mapping(Field::getName, Collectors.toSet()))) : new HashMap<>());
			node.setNodes(graph.stream().filter(item -> node.getProperties().get(node.getProperties().entrySet().stream().filter(entry -> entry.getKey().toLowerCase().contains(item.getEntity().toLowerCase())).map(Map.Entry::getKey).findAny().orElse("")) != null).collect(Collectors.toList()));
		});

		return graph;
	}

	private Field[] setupFields(Class<?> clazz) {
		if (clazz.isInstance(Object.class)) {
			return null;
		}
		return ArrayUtils.addAll(clazz.getDeclaredFields(), setupFields(clazz.getSuperclass()));
	}

	private String getTypeName(Field field) {
		try {
			ParameterizedType	parameterizedType	= (ParameterizedType) field.getGenericType();
			String				name				= field.getType().getSimpleName();

			return getCompleteTypeName(name, parameterizedType);
		} catch (Exception e) {
			return field.getType().getSimpleName();
		}
	}

	private String getCompleteTypeName(String name, ParameterizedType parameterizedType) {
		try {
			Type[]		types		= parameterizedType != null? parameterizedType.getActualTypeArguments() : null;

			if (types != null && types.length > 0) {
				name = name.concat("<");
				for (int i = 0; i < types.length; i++) {
					if (types[i] instanceof ParameterizedType) {
						name = name.concat(",").concat(((Class<?>) ((ParameterizedType) types[1]).getRawType()).getSimpleName());
						name = (getCompleteTypeName(name, (ParameterizedType) types[i]));
					} else {
						name = name.concat(((Class<?>) types[i]).getSimpleName());
					}
				}
			}
			return name.concat(">");
		} catch (Exception e) {
			return name;
		}
	}
}

class Node {
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
	private	transient	List<Node>					nodes;

	@Getter
	@Setter
	private	transient	boolean						visited;

	public Node(String table, String entity) {
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