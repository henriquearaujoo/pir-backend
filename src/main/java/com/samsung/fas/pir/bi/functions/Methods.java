package com.samsung.fas.pir.bi.functions;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Methods {
	private		static			Logger			Log			= LoggerFactory.getLogger(Methods.class);

	public static <T> Consumer<T> handle(Consumer<T> consumer, String message) {
		return i -> {
			try {
				consumer.accept(i);
			} catch (Exception e) {
				Log.error(message != null? message : e.getMessage());
			}
		};
	}

	public static Specification<?> dateSpecification(LocalDate date) {
		return (root, cq, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (date != (null)) {
				predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), date.toDate()));
			}
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}