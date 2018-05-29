package com.samsung.fas.pir.graph;

import com.querydsl.core.JoinType;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Component
public class Query {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private		EntityManager		manager;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 	Graph				graph;

	@Autowired
	public Query(EntityManager manager, Graph graph) {
		setManager(manager);
		setGraph(graph);
	}

	public Map<?, ?> query(@Nonnull Path root) {
		PathBuilder<?> 							grouper		= path(graph.getEntities().get(root.getEntity()));
		Map<Object, Map<String, List<Object>>>	map			= new HashMap<>();
		Map<Object, Map<String, List<Object>>>	response	= new HashMap<>();

		query(new JPAQuery<>(manager), root, null, grouper, map, root.getGroup());

		map.forEach((key, value) -> {
			try {
				if (key != null) {
					Class<?>					keyClass		= graph.getDtos().get(key.getClass().getSimpleName() + "DTO");																	// DTO Key Class
					Object						keyObject		= keyClass != null? keyClass.getDeclaredConstructor(key.getClass(), boolean.class).newInstance(key, false) : null;		// DTO Key Instance
					Map<String, List<Object>>	subMap			= new HashMap<>();																												// DTO Inner Map

					value.forEach((stringKey, valuesList) -> {
						try {
							ArrayList<Object>	values			= new ArrayList<>();																											// DTO Values
							for (Object v : valuesList) {
								Class<?>		valueClass		= graph.getDtos().get(v.getClass().getSimpleName() + "DTO");																	// DTO Value Class
								Object			valueObject		= valueClass != null? valueClass.getDeclaredConstructor(v.getClass(), boolean.class).newInstance(v, false) : null;		// DTO Value Object
								values.add(valueObject);
							}
							subMap.put(stringKey, values);
						} catch (Exception e) {
							e.printStackTrace();
						}
					});

					response.put(keyObject, subMap);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return response;
	}

	private void query(@Nonnull JPAQuery<?> query, @Nonnull Path root, PathBuilder<?> rootPath, @Nonnull PathBuilder<?> grouper, @Nonnull Map<Object, Map<String, List<Object>>> map, List<String> group) {
		Class<?>				clazz	= getGraph().getEntities().get(root.getEntity());
		PathBuilder<Object>		path	= path(clazz);

		if (root.getJoins() != null) {
			for (int i = 0; i < root.getJoins().size(); i++) {
				if (rootPath != null /*&& i == 0*/) {
					query.from(rootPath).getMetadata().addJoin(JoinType.FULLJOIN, path);
					query.getMetadata().addJoinCondition(Expressions.stringPath(rootPath, getPropertyName(path.getType(), rootPath.getType().getDeclaredFields()).concat("id")).eq(Expressions.stringPath(path, getPropertyName(rootPath.getType(), path.getType().getDeclaredFields()).concat("id"))));
				}
				query(root.getJoins().size() > 1? query.clone(manager) : query, root.getJoins().get(i), path, grouper, map, group);
			}
		} else {
			if (rootPath == null) {
				map.putAll(query.from(path).distinct().transform(groupBy(grouper).as(GroupBy.map(Expressions.constant(clazz.getSimpleName()), list(path)))));
			} else {
				query.from(rootPath).getMetadata().addJoin(JoinType.FULLJOIN, path);
				query.getMetadata().addJoinCondition(Expressions.stringPath(rootPath, getPropertyName(path.getType(), rootPath.getType().getDeclaredFields()).concat("id")).eq(Expressions.stringPath(path, getPropertyName(rootPath.getType(), path.getType().getDeclaredFields()).concat("id"))));

				for (String item : group) {
					Class<?>				itemClass	= getGraph().getEntities().get(item);
					PathBuilder<Object>		itemPath	= path(itemClass);
					query.distinct().transform(groupBy(grouper).as(GroupBy.map(Expressions.constant(itemClass.getSimpleName()), list(itemPath)))).forEach((k, v) -> v.forEach((key, value) -> map.merge(k, v, (old, cur) -> {
						old.merge(key, value, (o, n) -> Stream.concat(o.stream(), n.stream()).collect(Collectors.toList()));
						return old;
					})));
				}
			}
		}
	}

	private String getPropertyName(Class<?> type, Field[] fields) {
		for (Field field : fields) {
			if (field.getType() == type) {
				return field.getName().toLowerCase().concat(".");
			}
		}
		return "";
	}

	private PathBuilder<Object> path(@Nonnull Class<?> clazz) {
		return new PathBuilder<>(clazz, clazz.getSimpleName());
	}
}
