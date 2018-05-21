package com.samsung.fas.pir.graph;

import com.querydsl.core.JoinType;
import com.querydsl.core.group.AbstractGroupExpression;
import com.querydsl.core.group.Group;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.samsung.fas.pir.persistence.annotations.Alias;
import com.samsung.fas.pir.rest.dto.annotations.DTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.querydsl.core.group.GroupBy.*;

@Component
public class Query {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		EntityManager		manager;

	@Autowired
	public Query(EntityManager manager) {
		setManager(manager);
	}

	public Map<?, ?> query(Path root) {
		EntityPath<?>			grouper		= path(findClass("com.samsung.fas.pir", root.getEntity()));
		Map<?, ?>				map			= query(root, null, null, null).transform(groupBy(grouper).as(listing(root.getGrouped()).toArray(new Expression<?>[0])));
		Map<Object, Object>		response	= new HashMap<>();

		map.forEach((key, value) -> {
			Class<?>	kclass		= key != null? findClass("com.samsung.fas.pir", key.getClass().getSimpleName() + "DTO", DTO.class): null;

			try {
				if (((Group) value) != null) {
					for (Object o : ((Group) value).toArray()) {
						Class<?>	vclass		= o != null? findClass("com.samsung.fas.pir", o.getClass().getSimpleName() + "DTO", DTO.class): null;
						response.put(
								kclass != null? kclass.getDeclaredConstructor(key.getClass(), boolean.class).newInstance(key, true) : "",
								vclass != null? vclass.getDeclaredConstructor(o.getClass(), boolean.class).newInstance(o, true) : null
						);
					}
				}
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
		});

		return response;
	}

	private List<Expression<?>> listing(List<String> grouped) {
		List<Expression<?>> as = new ArrayList<>();

		if (grouped != null)
			grouped.forEach(item -> as.add(list(path(findClass("com.samsung.fas.pir", item)))));

		return as;
	}

	private AbstractGroupExpression<?, ?> mapping(List<String> entities, EntityPath<?> parent) {
		if (parent == null) {
			return mapping(entities, path(findClass("com.samsung.fas.pir", entities.remove(0))));
		} else {
			if (entities.iterator().hasNext() && entities.size() > 1) {
				return map(parent, mapping(entities, path(findClass("com.samsung.fas.pir", entities.remove(0)))));
			} else {
				return map(parent, path(findClass("com.samsung.fas.pir", entities.remove(0))));
			}
		}
	}

	private JPAQuery<?> query(Path root, JPAQuery<?> query, EntityPathBase<?> rootPath, List<EntityPathBase<?>> paths) {
		Class<?>					clazz	= findClass("com.samsung.fas.pir", root.getEntity());
		EntityPathBase<?>			path	= path(clazz);


		if (paths != null) {
			paths.add(path);
		} else {
			paths = new ArrayList<>();
			paths.add(path);
		}

		if (query != null && path != null) {
			path.as(clazz.getAnnotation(Alias.class) != null? clazz.getAnnotation(Alias.class).value() : path.getMetadata().getName());
			query.getMetadata().addJoin(JoinType.FULLJOIN, path);
			query.getMetadata().addJoinCondition(Expressions.stringPath(rootPath, getPropertyName(path.getType(), rootPath.getType().getDeclaredFields()).concat("id")).eq(Expressions.stringPath(path, getPropertyName(rootPath.getType(), path.getType().getDeclaredFields()).concat("id"))));
		} else {
			query = new JPAQuery<>(manager).select(paths.toArray(new EntityPathBase<?>[0])).from(path);
		}

		if (root.getJoins() != null) {
			for (Path node : root.getJoins()) {
				query(node, query, path, paths);
			}
		}

		return query.select(paths.toArray(new EntityPathBase<?>[0]));
	}

	private String getPropertyName(Class<?> type, Field[] fields) {
		for (Field field : fields) {
			if (field.getType() == type) {
				return field.getName().toLowerCase().concat(".");
			}
		}
		return "";
	}

	@SuppressWarnings("SameParameterValue")
	private Class<?> findClass(String prefix, String className) {
		return new Reflections(prefix).getSubTypesOf(EntityPathBase.class).stream().filter(clazz -> clazz.getSimpleName().equalsIgnoreCase("Q" + className)).findAny().orElse(null);
	}

	@SuppressWarnings("SameParameterValue")
	private Class<?> findClass(String prefix, String className, Class<? extends Annotation> annotation) {
		return new Reflections(prefix).getTypesAnnotatedWith(annotation).stream().filter(clazz -> clazz.getSimpleName().equalsIgnoreCase(className)).findAny().orElse(null);
	}

	private EntityPathBase<?> path(Class<?> clazz) {
		try {
			return (EntityPathBase<?>) clazz.getDeclaredConstructor(String.class).newInstance(clazz.getSimpleName());
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}
}
