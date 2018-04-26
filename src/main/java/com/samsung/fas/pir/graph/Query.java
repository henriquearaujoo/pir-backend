package com.samsung.fas.pir.graph;

import com.querydsl.core.JoinType;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.samsung.fas.pir.persistence.annotations.Alias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Query {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		EntityManager		manager;

	@Autowired
	public Query(EntityManager manager) {
		setManager(manager);
	}

	public JPAQuery<?> query(Path root, JPAQuery<?> query, EntityPathBase<?> rootPath, List<EntityPathBase<?>> paths) {
		Class<?>					clazz	= findClass("com.samsung.fas.pir", root.getEntity(), Table.class);
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
	private Class<?> findClass(String prefix, String className, Class<? extends Annotation> annotation) {
		return new Reflections(prefix).getSubTypesOf(EntityPathBase.class).stream().filter(clazz -> clazz.getSimpleName().equalsIgnoreCase("Q" + className)).findAny().orElse(null);
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
