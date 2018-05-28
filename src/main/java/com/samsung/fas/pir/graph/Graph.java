package com.samsung.fas.pir.graph;

import com.samsung.fas.pir.persistence.annotations.Alias;
import com.samsung.fas.pir.rest.dto.annotations.DTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Graph {
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PRIVATE)
	private		List<Node>					graph;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PRIVATE)
	private 	Map<String, Class<?>>		dtos;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PRIVATE)
	private 	Map<String, Class<?>>		entities;

	public Graph() {
		setGraph(setupGraph("com.samsung.fas.pir"));
		setDtos(setupDTO("com.samsung.fas.pir"));
		setEntities(setupEntities("com.samsung.fas.pir"));
	}

	private Map<String, Class<?>> setupDTO(String prefix) {
		return new Reflections(prefix).getTypesAnnotatedWith(DTO.class).stream().collect(Collectors.toMap(Class::getSimpleName, value -> value));
	}

	private Map<String, Class<?>> setupEntities(String prefix) {
		return new Reflections(prefix).getTypesAnnotatedWith(Entity.class).stream().collect(Collectors.toMap(Class::getSimpleName, value -> value));
	}

	@SuppressWarnings("SameParameterValue")
	private List<Node> setupGraph(String prefix) {
		Set<Class<?>> 	classes	= new Reflections(prefix).getTypesAnnotatedWith(Table.class);
		List<Node>		graph	= classes.stream().map(item -> new Node(item.getAnnotation(Table.class).name(), item.getSimpleName(), item.getAnnotation(Alias.class) != null? item.getAnnotation(Alias.class).value() : null)).collect(Collectors.toList());

		graph.forEach(node -> {
			Class<?>	clazz	= classes.stream().filter(item -> item.getSimpleName().equalsIgnoreCase(node.getEntity())).findAny().orElse(null);
			node.setProperties(clazz != null? Arrays.stream(ArrayUtils.addAll(setupFields(clazz))).map(item -> new Property(item.getName(), getTypeName(item), item.getAnnotation(Alias.class) != null? item.getAnnotation(Alias.class).value() : null)).collect(Collectors.toList()) : new ArrayList<>());
			node.setNodes(graph.stream().filter(item ->  node.getProperties().stream().filter(n -> n.getType().toLowerCase().contains(item.getEntity().toLowerCase())).findAny().orElse(null) != null).collect(Collectors.toList()));
		});

		return graph;
	}

	// Recursion
	private Field[] setupFields(Class<?> clazz) {
		return ArrayUtils.addAll(clazz.getDeclaredFields(), clazz.isInstance(Object.class)? null : setupFields(clazz.getSuperclass()));
	}

	private String getTypeName(Field field) {
		try {
			ParameterizedType 	parameterizedType	= (ParameterizedType) field.getGenericType();
			String				name				= field.getType().getSimpleName();

			return getCompleteTypeName(name, parameterizedType);
		} catch (Exception e) {
			return field.getType().getSimpleName();
		}
	}

	// Recursion
	private String getCompleteTypeName(String name, ParameterizedType parameterizedType) {
		try {
			Type[]				types				= parameterizedType != null? parameterizedType.getActualTypeArguments() : null;

			if (types != null && types.length > 0) {
				name = name.concat("<");
				for (Type type : types) {
					if (type instanceof ParameterizedType) {
						name = name.concat(",").concat(((Class<?>) ((ParameterizedType) type).getRawType()).getSimpleName());
						name = (getCompleteTypeName(name, (ParameterizedType) type));
					} else {
						name = name.concat(((Class<?>) type).getSimpleName());
					}
				}
			}

			return name.concat(">");
		} catch (Exception e) {
			return name;
		}
	}
}
