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

		return new ResponseDTO(responseMap.entrySet().stream().map(item -> new MapDTO(item.getKey(), item.getValue())).collect(Collectors.toList()), table);
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
				table.addAll(setupTable(query, grouper, group, groupBy));
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

	private List<ColumnDTO> setupTable(JPAQuery<?> query, PathBuilder<?> root, List<String> groups, String groupBy) {
		PathBuilder<?>[]		paths		= Stream.concat(Stream.of(root), groups.stream().map(group -> path(getGraph().getEntities().get(group)))).toArray(PathBuilder[]::new);
		ArrayList<ColumnDTO>	table		= new ArrayList<>();

		query.select(paths).distinct().fetch().forEach(row -> {
			for (Object item : row.toArray()) {
				if (item != null) {
					String		entity		= item.getClass().getAnnotation(Alias.class) != null? item.getClass().getAnnotation(Alias.class).value() : null;
					Field[]		fields		= item.getClass().getDeclaredFields();

					for (Field field : fields) {
						field.setAccessible(true);
						try {
							String		key		= field.getAnnotation(Alias.class) != null? field.getAnnotation(Alias.class).value() : null;
							Object		value	= field.get(item);
							ColumnDTO	column	= table.stream().filter(c -> c.getColumn().equalsIgnoreCase(key) && c.getEntity().equalsIgnoreCase(entity)).findAny().orElse(null);

							if (entity != null && key != null) {
								if (column != null) {
									if (column.getValues() != null) {
										column.getValues().add(String.valueOf(value));
									} else {
										column.setValues(new ArrayList<>(Collections.singletonList(String.valueOf(value))));
									}
									table.add(column);
								} else {
									table.add(new ColumnDTO(entity, key, new ArrayList<>(Collections.singletonList(String.valueOf(value)))));
								}
							}
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});

		return regularMatrix(table, groupBy);
	}



	private List<ColumnDTO> regularMatrix(List<ColumnDTO> table, String groupBy) {
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
