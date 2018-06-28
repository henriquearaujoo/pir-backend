package com.samsung.fas.pir.persistence.dao.utils;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class SBPage {
	@SuppressWarnings("unchecked")
	public static Query setupPage(JPAQuery query, Pageable pageable, PathBuilder entityPath) {
		JPAQuery result = (JPAQuery) query.clone();

		if (pageable != null) {
			for (Sort.Order order : pageable.getSort()) {
				PathBuilder path = entityPath.get(order.getProperty());
				query.orderBy(new OrderSpecifier(Order.valueOf(order.getDirection().name()), path));
			}
		}
		try {
			return query.createQuery();
		} catch (Exception e) {
			return result.createQuery();
		}
	}

	@SuppressWarnings("unchecked")
	public static Page<?> getPageList(Pageable pageable, Query query, Function groupBy) {
		final List<?> list = query.getResultList();
		try {
			if (pageable.getPageSize() > list.size())
				return new PageImpl<>(list.subList(((Long) pageable.getOffset()).intValue(), ((Long) (pageable.getOffset() + list.size())).intValue()), pageable, groupBy != null? ((HashMap) list.stream().collect(Collectors.groupingBy(groupBy, toList()))).size() : list.size());
			return new PageImpl<>(list.subList(((Long) pageable.getOffset()).intValue(), ((Long) (pageable.getOffset() + pageable.getPageSize())).intValue()), pageable,groupBy != null? ((HashMap) list.stream().collect(Collectors.groupingBy(groupBy, toList()))).size() : list.size());
		} catch (Exception e) {
			try {
				return new PageImpl<>(list.subList(((Long) pageable.getOffset()).intValue(), ((Long) (pageable.getOffset() + 1)).intValue()), pageable, groupBy != null? ((HashMap) list.stream().collect(Collectors.groupingBy(groupBy, toList()))).size() : list.size());
			} catch (Exception e1) {
				return new PageImpl<>(new ArrayList<>(), pageable, groupBy != null? ((HashMap) list.stream().collect(Collectors.groupingBy(groupBy, toList()))).size() : list.size());
			}
		}
	}
}