package com.samsung.fas.pir.graph;

import com.samsung.fas.pir.persistence.annotations.Alias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Graph {
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PRIVATE)
	private		List<Node>		graph;

	public Graph() {
		setGraph(setup("com.samsung.fas.pir"));
	}

	@SuppressWarnings("SameParameterValue")
	private List<Node> setup(String prefix) {
		Set<Class<?>> 	classes	= new Reflections(prefix).getTypesAnnotatedWith(Table.class);
		List<Node>		graph	= classes.stream().map(item -> new Node(item.getAnnotation(Table.class).name(), item.getSimpleName())).collect(Collectors.toList());

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
