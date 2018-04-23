package com.samsung.fas.pir.graph;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
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
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;

@Component
public class Query {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		EntityManager		manager;

	@Autowired
	public Query(EntityManager manager) {
		setManager(manager);
		doQuery(null);
	}

	public void doQuery(LinkedList<Node> path) {
		Class<?>			clazz		= findClass("com.samsung.fas.pir", "Visit", Table.class);
		JPAQuery<?>			query		= new JPAQuery<>(manager);

		if (clazz != null)
			query.from(path(clazz));
	}

	@SuppressWarnings("SameParameterValue")
	private Class<?> findClass(String prefix, String className, Class<? extends Annotation> annotation) {
		return new Reflections(prefix).getSubTypesOf(EntityPathBase.class).stream().filter(clazz -> clazz.getSimpleName().equalsIgnoreCase(className)).findAny().orElse(null);
	}

	private EntityPathBase<?> path(Class<?> clazz) {
		try {
			return (EntityPathBase<?>) Objects.requireNonNull(findClass("com.samsung.fas.pir", "Q" + clazz.getSimpleName(), Generated.class)).getDeclaredConstructor(String.class).newInstance(clazz.getSimpleName().toLowerCase());
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}
}
