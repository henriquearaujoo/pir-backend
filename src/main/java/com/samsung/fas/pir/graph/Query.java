package com.samsung.fas.pir.graph;

import com.google.gson.GsonBuilder;
import com.querydsl.core.JoinType;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.samsung.fas.pir.persistence.models.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Criteria;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Query {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		EntityManager		manager;

	@Autowired
	public Query(EntityManager manager) {
		setManager(manager);

		Path chapter = new Path();
		chapter.setEntity("Chapter");

		Path child = new Path();
		child.setEntity("Child");

		Path mother = new Path();
		mother.setEntity("Mother");
		mother.setJoins(new ArrayList<>());

		Path responsible = new Path();
		responsible.setEntity("Responsible");
		responsible.setJoins(new ArrayList<>());
		responsible.getJoins().add(mother);
		responsible.getJoins().add(child);

		Path agent = new Path();
		agent.setEntity("User");

		Path visit = new Path();
		visit.setEntity("Visit");
		visit.setJoins(new ArrayList<>());
		visit.getJoins().add(agent);
		visit.getJoins().add(chapter);
		visit.getJoins().add(responsible);

		Class<?> clazz = findClass("com.samsung.fas.pir", "Mother", Table.class);
		Class<?> clazz2 = findClass("com.samsung.fas.pir", "Child", Table.class);

		System.out.println(getPropertyName(clazz, clazz2.getDeclaredFields()));


		System.out.println(query(visit, null, null, null).toString());
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
