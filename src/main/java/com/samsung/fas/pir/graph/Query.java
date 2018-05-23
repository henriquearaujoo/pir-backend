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
import com.samsung.fas.pir.persistence.models.Visit;
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

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

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
		PathBuilder<Object> 	grouper		= path(findClass("com.samsung.fas.pir", root.getEntity(), Entity.class), null);
//		Map<?, ?>				map			= query(new JPAQuery<>(manager), root, null, null).select(paths(addElement(root.getGrouped(), root.getEntity()))).transform(groupBy(grouper).as(listing(root.getGrouped()).toArray(new Expression[0])));
		Map<?, ?> 				map 		= query(new JPAQuery<>(manager), root, null, null).transform(groupBy(grouper).as(list(new PathBuilder<>(Visit.class, "Child_Visit")), list(new PathBuilder<>(Visit.class, "Responsible_Visit"))));
		Map<Object, Object>		response	= new HashMap<>();


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
							response.put(kobj, values);
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

	private JPAQuery<?> query(JPAQuery<?> query, Path root, PathBuilder<?> rootPath, String identifier) {
		PathBuilder<?>		path	= path(findClass("com.samsung.fas.pir", root.getEntity(), Entity.class), identifier);

		if (root.getJoins() != null) {
			for (int i = 0; i < root.getJoins().size(); i++) {
				if (rootPath != null && i == 0) {
					query.from(rootPath).getMetadata().addJoin(JoinType.FULLJOIN, path);
					query.getMetadata().addJoinCondition(Expressions.stringPath(rootPath, getPropertyName(path.getType(), rootPath.getType().getDeclaredFields()).concat("id")).eq(Expressions.stringPath(path, getPropertyName(rootPath.getType(), path.getType().getDeclaredFields()).concat("id"))));
				}
				query(query, root.getJoins().get(i), path, root.getEntity() + "_");
			}
			return query;
		}

		query.from(rootPath).getMetadata().addJoin(JoinType.FULLJOIN, path);
		query.getMetadata().addJoinCondition(Expressions.stringPath(rootPath, getPropertyName(path.getType(), rootPath.getType().getDeclaredFields()).concat("id")).eq(Expressions.stringPath(path, getPropertyName(rootPath.getType(), path.getType().getDeclaredFields()).concat("id"))));
		return query;
	}


	private JPAQuery<?> queryAny(Path root) {
		PathBuilder<?>		path	= path(findClass("com.samsung.fas.pir", root.getEntity(), Entity.class), null);

		return null;
	}


	private JPAQuery<?> queryOld(Path root, JPAQuery<?> query, EntityPathBase<?> rootPath, List<PathBuilder<?>> paths) {
		Class<?>			clazz	= findClass("com.samsung.fas.pir", root.getEntity(), Entity.class);
		PathBuilder<?>		path	= path(clazz, null);

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
			grouped.forEach(item -> as.add(list(path(findClass("com.samsung.fas.pir", item, Entity.class), null))));

		return as;
	}

	private EntityPath<?>[] paths(List<String> pathstrs) {
		List<EntityPath<?>> paths = new ArrayList<>();

		for (String path : pathstrs) {
			paths.add(path(findClass("com.samsung.fas.pir", path, Entity.class), null));
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

	private PathBuilder<Object> path(Class<?> clazz, String identifier) {
		return new PathBuilder<>(clazz, identifier != null? identifier.concat(clazz.getSimpleName()) : clazz.getSimpleName());
	}
}
