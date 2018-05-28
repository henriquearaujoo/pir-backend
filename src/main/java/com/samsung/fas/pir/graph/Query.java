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

	private Object toDTO(Object entity) {
		try {
			Class<?>	clazz	= entity != null? graph.getDtos().get(entity.getClass().getSimpleName() + "DTO") : null;
			return clazz != null? clazz.getDeclaredConstructor(entity.getClass(), boolean.class).newInstance(entity, false) : null;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Map<?, ?> query(@Nonnull Path root) {
		PathBuilder<?> 							grouper		= path(graph.getEntities().get(root.getEntity()));
		Map<Object, Map<String, List<Object>>>	map			= new HashMap<>();
		Map<Object, Map<String, List<Object>>>	response	= new HashMap<>();

		query(new JPAQuery<>(manager), root, null, grouper, map);

		map.forEach((key, value) -> {
			try {
				if (key != null) {
					Class<?>					kclass		= key != null? graph.getDtos().get(key.getClass().getSimpleName() + "DTO") : null;
					Object						kobj		= kclass != null? kclass.getDeclaredConstructor(key.getClass(), boolean.class).newInstance(key, false) : null;
					ArrayList<Object>			values		= new ArrayList<>();
					Map<String, List<Object>>	submap		= new HashMap<>();

					if (value != null) {
						value.forEach((subkey, subvalue) -> {
							for (Object v : subvalue) {
								try {
									Class<?>	vclass		= v != null? graph.getDtos().get(v.getClass().getSimpleName() + "DTO") : null;
									Object		vobj		= vclass != null? vclass.getDeclaredConstructor(v.getClass(), boolean.class).newInstance(v, false) : null;
									values.add(vobj);
								} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
									e.printStackTrace();
								}
							}
							submap.put(subkey, values);
							response.put(kobj, submap);
						});
					}
				}
			} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
				e.printStackTrace();
			}
		});

		return response;
	}

	private void query(@Nonnull JPAQuery<?> query, @Nonnull Path root, PathBuilder<?> rootPath, @Nonnull PathBuilder<?> grouper, @Nonnull Map<Object, Map<String, List<Object>>> map) {
		Class<?>				clazz	= getGraph().getEntities().get(root.getEntity());
		PathBuilder<Object>		path	= path(clazz);

		if (root.getJoins() != null) {
			for (int i = 0; i < root.getJoins().size(); i++) {
				if (rootPath != null /*&& i == 0*/) {
					query.from(rootPath).getMetadata().addJoin(JoinType.FULLJOIN, path);
					query.getMetadata().addJoinCondition(Expressions.stringPath(rootPath, getPropertyName(path.getType(), rootPath.getType().getDeclaredFields()).concat("id")).eq(Expressions.stringPath(path, getPropertyName(rootPath.getType(), path.getType().getDeclaredFields()).concat("id"))));
				}
				query(root.getJoins().size() > 1? query.clone(manager) : query, root.getJoins().get(i), path, grouper, map);
			}
		} else {
			if (rootPath == null) {
				map.putAll(query.from(path).distinct().transform(groupBy(grouper).as(GroupBy.map(Expressions.constant(clazz.getSimpleName()), list(path)))));
			} else {
				query.from(rootPath).getMetadata().addJoin(JoinType.FULLJOIN, path);
				query.getMetadata().addJoinCondition(Expressions.stringPath(rootPath, getPropertyName(path.getType(), rootPath.getType().getDeclaredFields()).concat("id")).eq(Expressions.stringPath(path, getPropertyName(rootPath.getType(), path.getType().getDeclaredFields()).concat("id"))));
				map.putAll(query.distinct().transform(groupBy(grouper).as(GroupBy.map(Expressions.constant(clazz.getSimpleName()), list(path)))));
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
