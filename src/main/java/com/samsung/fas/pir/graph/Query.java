package com.samsung.fas.pir.graph;

import com.querydsl.core.JoinType;
import com.querydsl.core.group.Group;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.samsung.fas.pir.persistence.annotations.Alias;
import com.samsung.fas.pir.rest.dto.annotations.DTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
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
		PathBuilder<Object> 	grouper		= path(findClass("com.samsung.fas.pir", root.getEntity(), Entity.class));
		Map<?, ?>				map			= query(new JPAQuery<>(manager), root, null).select(paths(addElement(root.getGrouped(), root.getEntity()))).transform(groupBy(grouper).as(listing(root.getGrouped()).toArray(new Expression[0])));
		Map<Object, Object>		response	= new HashMap<>();

		queryAny(root);

		map.forEach((key, value) -> {
			Class<?>	kclass				= key != null? findClass("com.samsung.fas.pir", key.getClass().getSimpleName() + "DTO", DTO.class): null;

			try {
				Object				kobj		= kclass != null? kclass.getDeclaredConstructor(key.getClass(), boolean.class).newInstance(key, false) : "";
				ArrayList<Object>	values		= new ArrayList<>();

				if (value != null) {
					for (int i = 0; i < ((Group) value).toArray().length; i++) {
						if (((Group) value).toArray()[i] instanceof ArrayList) {
							for (Object o : (ArrayList<?>) ((Group) value).toArray()[i]) {
								Class<?>	vclass		= o != null? findClass("com.samsung.fas.pir", o.getClass().getSimpleName() + "DTO", DTO.class): null;
								values.add(vclass != null? vclass.getDeclaredConstructor(o.getClass(), boolean.class).newInstance(o, false) : null);
							}
						}
						response.put(kobj, values);
					}
				}
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
		});

		return response;
	}

	private JPAQuery<?> query(JPAQuery<?> query, Path root, PathBuilder<?> rootPath) {
		PathBuilder<?>		path	= path(findClass("com.samsung.fas.pir", root.getEntity(), Entity.class));

		if (root.getJoins() != null) {
			for (Path node : root.getJoins()) {
				query = query(query, node, path);
				if (rootPath != null) {
					query.getMetadata().addJoin(JoinType.INNERJOIN, rootPath);
					query.getMetadata().addJoinCondition(Expressions.stringPath(rootPath, getPropertyName(path.getType(), rootPath.getType().getDeclaredFields()).concat("id")).eq(Expressions.stringPath(path, getPropertyName(rootPath.getType(), path.getType().getDeclaredFields()).concat("id"))));
				}
			}
			return query;
		}

		query.from(path).getMetadata().addJoin(JoinType.INNERJOIN, rootPath);
		query.getMetadata().addJoinCondition(Expressions.stringPath(rootPath, getPropertyName(path.getType(), rootPath.getType().getDeclaredFields()).concat("id")).eq(Expressions.stringPath(path, getPropertyName(rootPath.getType(), path.getType().getDeclaredFields()).concat("id"))));
		return query.fetchJoin();
	}


	private JPAQuery<?> queryAny(Path root) {
		PathBuilder<?>		path	= path(findClass("com.samsung.fas.pir", root.getEntity(), Entity.class));

		return null;
	}


	private JPAQuery<?> queryOld(Path root, JPAQuery<?> query, EntityPathBase<?> rootPath, List<PathBuilder<?>> paths) {
		Class<?>			clazz	= findClass("com.samsung.fas.pir", root.getEntity(), Entity.class);
		PathBuilder<?>		path	= path(clazz);

		if (paths != null) {
			paths.add(path);
		} else {
			paths = new ArrayList<>();
			paths.add(path);
		}

		if (query != null) {
			path.as(clazz.getAnnotation(Alias.class) != null? clazz.getAnnotation(Alias.class).value() : path.getMetadata().getName());
			query.getMetadata().addJoin(JoinType.FULLJOIN, path);
			query.getMetadata().addJoinCondition(Expressions.stringPath(rootPath, getPropertyName(path.getType(), rootPath.getType().getDeclaredFields()).concat("id")).eq(Expressions.stringPath(path, getPropertyName(rootPath.getType(), path.getType().getDeclaredFields()).concat("id"))));
		} else {
			query = new JPAQuery<>(manager).select(paths.toArray(new EntityPathBase<?>[0])).from(path);
		}

		if (root.getJoins() != null) {
			for (Path node : root.getJoins()) {
				queryOld(node, query, path, paths);
			}
		}

		return query.select(paths.toArray(new PathBuilder<?>[0]));
	}

	private List<Expression<?>> listing(List<String> grouped) {
		List<Expression<?>> as = new ArrayList<>();

		if (grouped != null)
			grouped.forEach(item -> as.add(list(path(findClass("com.samsung.fas.pir", item, Entity.class)))));

		return as;
	}

	private EntityPath<?>[] paths(List<String> pathstrs) {
		List<EntityPath<?>> paths = new ArrayList<>();

		for (String path : pathstrs) {
			paths.add(path(findClass("com.samsung.fas.pir", path, Entity.class)));
		}

		return paths.toArray(new EntityPath[0]);
	}

	private List<String> addElement(List<String> source, String element) {
		source.add(element);
		return source;
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
		return new Reflections(prefix).getTypesAnnotatedWith(annotation).stream().filter(clazz -> clazz.getSimpleName().equalsIgnoreCase(className)).findAny().orElse(null);
	}

	private PathBuilder<Object> path(Class<?> clazz) {
		return new PathBuilder<>(clazz, clazz.getSimpleName());
	}
}
