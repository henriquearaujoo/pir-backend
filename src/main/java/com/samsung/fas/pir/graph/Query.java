package com.samsung.fas.pir.graph;

import com.querydsl.core.JoinType;
import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.samsung.fas.pir.graph.dto.ColumnDTO;
import com.samsung.fas.pir.graph.dto.MapDTO;
import com.samsung.fas.pir.graph.dto.PathDTO;
import com.samsung.fas.pir.graph.dto.ResponseDTO;
import com.samsung.fas.pir.persistence.annotations.Alias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
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
		Map<Object, Map<String, List<Object>>>	map			= new HashMap<>();
		Map<Object, Map<String, List<Object>>>	responseMap	= new HashMap<>();
		List<ColumnDTO>              			table       = new ArrayList<>();

		query(new JPAQuery<>(manager), root, null, grouper, map, root.getGroup(), table, root.getEntity());

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

//		setupTable(map, table);
		return new ResponseDTO(responseMap.entrySet().stream().map(item -> new MapDTO(item.getKey(), item.getValue())).collect(Collectors.toList()), null);
	}

	@SuppressWarnings("Duplicates")
	private void setupTable(Map<Object, Map<String, List<Object>>> map, List<ColumnDTO> table) {
		map.forEach((key, value) -> {
			Field[]		keyFields		= key.getClass().getDeclaredFields();
			String		keyEntity		= key.getClass().getAnnotation(Alias.class) != null? key.getClass().getAnnotation(Alias.class).value() : null;

			for (Field keyField : keyFields) {
				keyField.setAccessible(true);
				try {
					String		columnName		= keyField.getAnnotation(Alias.class) != null? keyField.getAnnotation(Alias.class).value() : null;
					Object		columnValue		= keyField.get(key);
					ColumnDTO	column			= table.stream().filter(c -> c.getEntity().equalsIgnoreCase(keyEntity)).filter(c -> c.getColumn().equalsIgnoreCase(columnName)).findAny().orElse(null);

					if (keyEntity != null && columnName != null) {
						if (column != null) {
							if (column.getValues() != null) {
								column.getValues().add(String.valueOf(columnValue));
							} else {
								column.setValues(new ArrayList<>(Collections.singletonList(String.valueOf(columnValue))));
							}
						} else {
							table.add(new ColumnDTO(keyEntity, columnName, new ArrayList<>(Collections.singletonList(String.valueOf(columnValue)))));
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	@SuppressWarnings("Duplicates")
	private void setupOtherColumns(List<Map<String, List<Object>>> list, List<ColumnDTO> table, String parent) {
		List<ColumnDTO>		parentColumns		= table.stream().filter(item -> item.getEntity().equalsIgnoreCase(parent)).collect(Collectors.toList());

		list.forEach(map -> {
			map.forEach((key, value) -> {
				parentColumns.forEach(col -> {
					//
				});

				value.forEach(object -> {
					Field[]		fields		= object.getClass().getDeclaredFields();
					String		entity		= object.getClass().getAnnotation(Alias.class) != null? object.getClass().getAnnotation(Alias.class).value() : null;

				});
			});
		});
	}

	private void query(@Nonnull JPAQuery<?> query, @Nonnull PathDTO root, PathBuilder<?> rootPath, @Nonnull PathBuilder<?> grouper, @Nonnull Map<Object, Map<String, List<Object>>> map, List<String> group, List<ColumnDTO> table, String groupBy) {
		Class<?>					clazz	= getGraph().getEntities().get(root.getEntity());
		PathBuilder<Object>			path	= path(clazz);

		if (root.getJoins() != null) {
			for (int i = 0; i < root.getJoins().size(); i++) {
				if (rootPath != null /*&& i == 0*/) {
					query.from(rootPath).getMetadata().addJoin(JoinType.FULLJOIN, path);
					query.getMetadata().addJoinCondition(Expressions.stringPath(rootPath, getPropertyName(path.getType(), rootPath.getType().getDeclaredFields()).concat("id")).eq(Expressions.stringPath(path, getPropertyName(rootPath.getType(), path.getType().getDeclaredFields()).concat("id"))));
				}
				query(root.getJoins().size() > 1? query.clone(manager) : query, root.getJoins().get(i), path, grouper, map, group, table, groupBy);
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

	private List<ColumnDTO> normalize(List<ColumnDTO> table, String groupBy) {
		Class<?>	clazz		= getGraph().getEntities().get(groupBy);
		ColumnDTO	column		= table.stream().max(Comparator.comparingInt(itemA -> itemA.getValues().size())).orElse(null);
		long		size		= column != null? column.getValues().size() : 0L;

		for (ColumnDTO item : table) {
			if (!item.getEntity().equalsIgnoreCase(clazz.getAnnotation(Alias.class).value())) {
				if (item.getValues().size() < size) {
					for (long i = item.getValues().size(); i < size; i++) {
						item.getValues().add(null);
					}
				}
			}
		}

		return table;
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
