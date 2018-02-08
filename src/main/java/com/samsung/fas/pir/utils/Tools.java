package com.samsung.fas.pir.utils;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.samsung.fas.pir.persistence.models.entity.Chapter;
import com.samsung.fas.pir.persistence.models.entity.Conclusion;
import com.samsung.fas.pir.persistence.models.entity.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.Query;
import java.util.Set;

public class Tools {
	public static float calculate(Chapter entity) {
		Conclusion c			= entity.getConclusion();
		float		complete 	= 25.0f;

		if (c != null) {
			Set<Question> qs = c.getQuestions();
			complete += 12.5f;
			if (qs != null) {
				final int[] questionsWithAnswers = {0};
				qs.forEach(item -> {
					if (item.getAnswers() != null) {
						questionsWithAnswers[0]++;
					}
				});
				if (qs.size() != 0)
					complete += (12.5 * (questionsWithAnswers[0]/qs.size()) );
			}
		}

		if (entity.getGreetings() != null) {
			complete	+= 25.0f;
		}

		if (entity.getIntervention() != null) {
			complete	+= 25.0f;
		}

		return complete;
	}

	public static Query setupPage(JPAQuery query, Pageable pageable, PathBuilder entityPath) {
		JPAQuery result = (JPAQuery) query.clone();

		if (pageable != null) {
			query.offset(pageable.getOffset());
			query.limit(pageable.getPageSize());
			if (pageable.getSort() != null) {
				for (Sort.Order order : pageable.getSort()) {
					PathBuilder path = entityPath.get(order.getProperty());
					//noinspection unchecked
					query.orderBy(new OrderSpecifier(Order.valueOf(order.getDirection().name()), path));
				}
			}
		}
		try {
			return query.createQuery();
		} catch (Exception e) {
			return result.createQuery();
		}
	}
}
