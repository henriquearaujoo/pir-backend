package com.samsung.fas.pir.graph;

import com.querydsl.core.JoinType;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.samsung.fas.pir.persistence.models.Chapter;
import com.samsung.fas.pir.persistence.models.QVisit;
import com.samsung.fas.pir.persistence.models.Visit;
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
		List<?> list = doQuery(new ArrayList<>(Arrays.asList("Visit", "Child", "Mother"))).fetch();
		System.out.println(doQuery(new ArrayList<>(Arrays.asList("Visit", "Child", "Mother"))).fetchAll());
	}

	public JPAQuery<?> doQuery(ArrayList<String> classes) {
		JPAQuery<?> 				query	= new JPAQuery<>(manager);
		EntityPathBase<?>[]			paths	= classes.stream().map(className -> path(findClass("com.samsung.fas.pir", className, Table.class))).toArray(EntityPathBase<?>[]::new);
		query 								= query.select(paths).from(path(findClass("com.samsung.fas.pir", classes.get(0), Table.class)));

		for (int i = 1; i < paths.length; i++) {
			// TODO: Change id for FK_ID
			query.getMetadata().addJoin(JoinType.FULLJOIN, paths[i]);
			query.getMetadata().addJoinCondition(Expressions.stringPath(paths[i - 1], "id").eq(Expressions.stringPath(paths[i], "id")));
		}

		return query;
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
