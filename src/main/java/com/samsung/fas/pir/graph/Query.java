package com.samsung.fas.pir.graph;

import com.querydsl.core.JoinType;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.samsung.fas.pir.rest.dto.annotations.DTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.lang.annotation.Annotation;
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

	@Autowired
	public Query(EntityManager manager) {
		setManager(manager);
	}

	public Map<?, ?> query(@Nonnull Path root) {
		PathBuilder<?> 						grouper		= path(findClass("com.samsung.fas.pir", root.getEntity(), Entity.class));
		Map<Object, Object>					map			= new HashMap<>();
		Map<Object, Map<String, Object>>	response	= new HashMap<>();

		query(new JPAQuery<>(manager), root, null, grouper, map);

		map.forEach((key, obj) -> {
			try {
				Class<?>			kclass		= key != null? findClass("com.samsung.fas.pir", key.getClass().getSimpleName() + "DTO", DTO.class): null;
				Object				kobj		= kclass != null? kclass.getDeclaredConstructor(key.getClass(), boolean.class).newInstance(key, false) : null;
				Map<String, Object>	emap		= new HashMap<>();
				ArrayList<Object>	values		= new ArrayList<>();

				if (obj != null) {
					if (obj.getClass().isInstance(new ArrayList<>())) {
						if (kobj != null) {
							for (Object value : (List) obj) {
								Class<?>	vclass		= value != null? findClass("com.samsung.fas.pir", value.getClass().getSimpleName() + "DTO", DTO.class): null;
								Object		vobj		= vclass != null? vclass.getDeclaredConstructor(value.getClass(), boolean.class).newInstance(value, false) : null;
								values.add(vobj);
								emap.put(value != null? value.getClass().getSimpleName() : "VOID", values);
							}
							response.put(kobj, emap);
						}
					} else {
						if (kobj != null) {
							Class<?>	vclass		= findClass("com.samsung.fas.pir", obj.getClass().getSimpleName() + "DTO", DTO.class);
							Object		vobj		= vclass != null? vclass.getDeclaredConstructor(obj.getClass(), boolean.class).newInstance(obj, false) : null;
							emap.put(obj.getClass().getSimpleName(), Collections.singletonList(vobj));
							response.put(kobj, emap);
						}
					}
				}
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
		});

		return response;
	}

	private void query(@Nonnull JPAQuery<?> query, @Nonnull Path root, PathBuilder<?> rootPath, @Nonnull PathBuilder<?> grouper, @Nonnull Map<Object, Object> map) {
		Class<?>			clazz	= findClass("com.samsung.fas.pir", root.getEntity(), Entity.class);
		PathBuilder<?>		path	= path(clazz);

		if (root.getJoins() != null) {
			for (int i = 0; i < root.getJoins().size(); i++) {
				if (rootPath != null /*&& i == 0*/) {
					query.from(rootPath).getMetadata().addJoin(JoinType.FULLJOIN, path);
					query.getMetadata().addJoinCondition(Expressions.stringPath(rootPath, getPropertyName(path.getType(), rootPath.getType().getDeclaredFields()).concat("id")).eq(Expressions.stringPath(path, getPropertyName(rootPath.getType(), path.getType().getDeclaredFields()).concat("id"))));
				}
				query(query.clone(manager), root.getJoins().get(i), path, grouper, map);
			}
		} else {
			if (rootPath == null) {
				map.putAll(query.from(path).distinct().transform(groupBy(grouper).as(list(new PathBuilder<Object>(clazz, clazz.getSimpleName())))));
			} else {
				query.from(rootPath).getMetadata().addJoin(JoinType.FULLJOIN, path);
				query.getMetadata().addJoinCondition(Expressions.stringPath(rootPath, getPropertyName(path.getType(), rootPath.getType().getDeclaredFields()).concat("id")).eq(Expressions.stringPath(path, getPropertyName(rootPath.getType(), path.getType().getDeclaredFields()).concat("id"))));
				query.distinct().transform(groupBy(grouper).as(list(new PathBuilder<Object>(clazz, clazz.getSimpleName())))).forEach((k, v) -> map.merge(k, v, (o, n) -> Stream.concat(((List<?>) o).stream(), ((List<?>) n).stream()).collect(Collectors.toList())));
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

	@SuppressWarnings("SameParameterValue")
	private Class<?> findClass(@Nonnull String prefix, @Nonnull String className, @Nonnull Class<? extends Annotation> annotation) {
		return new Reflections(prefix).getTypesAnnotatedWith(annotation).stream().filter(clazz -> clazz.getSimpleName().equalsIgnoreCase(className)).findAny().orElse(null);
	}

	private PathBuilder<Object> path(@Nonnull Class<?> clazz) {
		return new PathBuilder<>(clazz, clazz.getSimpleName());
	}
}
