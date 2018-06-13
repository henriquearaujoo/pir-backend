package com.samsung.fas.pir.graph;

import com.querydsl.core.JoinType;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.samsung.fas.pir.graph.annotations.Alias;
import com.samsung.fas.pir.graph.dto.ColumnDTO;
import com.samsung.fas.pir.graph.dto.MapDTO;
import com.samsung.fas.pir.graph.dto.PathDTO;
import com.samsung.fas.pir.graph.dto.ResponseDTO;
import com.samsung.fas.pir.persistence.models.base.BaseID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import java.lang.reflect.Field;
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

	public ResponseDTO query(@Nonnull PathDTO root) {
		PathBuilder<?> 							grouper		= path(graph.getEntities().get(root.getEntity()));
		Map<Object, Map<String, List<Object>>>	map			= new LinkedHashMap<>();
		Map<Object, Map<String, List<Object>>>	responseMap	= new LinkedHashMap<>();
		List<ColumnDTO>              			table       = new ArrayList<>();

		query(new JPAQuery<>(manager), root, null, grouper, map);

		map = map.entrySet().stream().sorted(Map.Entry.comparingByKey((keyA, keyB) -> (int) (((BaseID) keyA).getId() - ((BaseID) keyB).getId()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o, n) -> o, LinkedHashMap::new));

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

					responseMap.put(keyObject, subMap);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		setupTable(map, table);
		return new ResponseDTO(responseMap.entrySet().stream().map(item -> new MapDTO(item.getKey(), item.getValue())).collect(Collectors.toList()), table);
	}

	private void setupTable(Map<Object, Map<String, List<Object>>> map, List<ColumnDTO> table) {
		map.forEach((key, value) -> {
			if (key != null) {
				Field[]		fields		= key.getClass().getDeclaredFields();
				String		entity		= key.getClass().getAnnotation(Alias.class) != null? key.getClass().getAnnotation(Alias.class).value() : null;
				String		className	= key.getClass().getSimpleName();
				value.forEach((name, list) -> {
					if (list.size() > 0) {
						list.forEach(item -> {
							Field[]		itemFields		= item.getClass().getDeclaredFields();
							String		itemName		= item.getClass().getAnnotation(Alias.class) != null? item.getClass().getAnnotation(Alias.class).value() : null;
							addValuesToTable(entity, key, fields, table);
							if (!name.equalsIgnoreCase(className)) {
								addValuesToTable(itemName, item, itemFields, table);
							}
						});
					} else {
						addValuesToTable(entity, key, fields, table);
						addValuesToTable(graph.getEntities().get(name).getAnnotation(Alias.class).value(), null, graph.getEntities().get(name).getDeclaredFields(), table);
					}
				});
			}
		});
	}

	private void addValuesToTable(String entityName, Object entity, Field[] fields, List<ColumnDTO> table) {
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				String		columnName		= field.getAnnotation(Alias.class) != null? field.getAnnotation(Alias.class).value() : null;
				String		columnType		= field.getType().getSimpleName();
				Object		columnValue		= entity != null? field.get(entity) : null;
				ColumnDTO	column			= table.stream().filter(c -> c.getEntity().equalsIgnoreCase(entityName)).filter(c -> c.getColumn().equalsIgnoreCase(columnName)).findAny().orElse(null);

				if (entityName != null && columnName != null) {
					if (column != null) {
						if (column.getValues() != null) {
							column.getValues().add(String.valueOf(columnValue));
						} else {
							column.setValues(new ArrayList<>(Collections.singletonList(String.valueOf(columnValue))));
						}
					} else {
						table.add(new ColumnDTO(entityName, columnName, columnType, new ArrayList<>(Collections.singletonList(String.valueOf(columnValue)))));
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void query(@Nonnull JPAQuery<?> query, @Nonnull PathDTO root, PathBuilder<?> rootPath, @Nonnull PathBuilder<?> grouper, @Nonnull Map<Object, Map<String, List<Object>>> map) {
		Class<?>					clazz	= getGraph().getEntities().get(root.getEntity());
		PathBuilder<Object>			path	= path(clazz);

		if (root.isGroup()) {
			if (rootPath != null) {
				query.from(rootPath).getMetadata().addJoin(JoinType.LEFTJOIN, path);
				query.getMetadata().addJoinCondition(Expressions.stringPath(rootPath, getPropertyName(path.getType(), rootPath.getType().getDeclaredFields()).concat("id")).eq(Expressions.stringPath(path, getPropertyName(rootPath.getType(), path.getType().getDeclaredFields()).concat("id"))));
				query.from(rootPath).distinct().transform(groupBy(grouper).as(GroupBy.map(Expressions.constant(clazz.getSimpleName()), list(path)))).forEach((k, v) -> v.forEach((key, value) -> {
					map.merge(k, v, (old, cur) -> {
						old.merge(key, value, (o, n) -> Stream.concat(o.stream(), n.stream()).distinct().collect(Collectors.toList()));
						return old;
					});
				}));
			} else {
				query.from(grouper).distinct().transform(groupBy(grouper).as(GroupBy.map(Expressions.constant(clazz.getSimpleName()), list(path)))).forEach((k, v) -> v.forEach((key, value) -> {
					map.merge(k, v, (old, cur) -> {
						old.merge(key, value, (o, n) -> Stream.concat(o.stream(), n.stream()).distinct().collect(Collectors.toList()));
						return old;
					});
				}));
			}
		}
		if (root.getJoins() != null) {
			for (int i = 0; i < root.getJoins().size(); i++) {
				if (rootPath != null) {
					query.from(rootPath).getMetadata().addJoin(JoinType.LEFTJOIN, path);
					query.getMetadata().addJoinCondition(Expressions.stringPath(rootPath, getPropertyName(path.getType(), rootPath.getType().getDeclaredFields()).concat("id")).eq(Expressions.stringPath(path, getPropertyName(rootPath.getType(), path.getType().getDeclaredFields()).concat("id"))));
				}
				query(query.clone(manager), root.getJoins().get(i), path, grouper, map);
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
