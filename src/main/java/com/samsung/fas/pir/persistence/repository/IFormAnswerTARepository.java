package com.samsung.fas.pir.persistence.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.entity.FormAnswerTA;
import com.samsung.fas.pir.persistence.models.entity.QFormAnswerTA;
import com.samsung.fas.pir.persistence.repository.base.BRepository;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface IFormAnswerTARepository extends BRepository<FormAnswerTA, Long, QFormAnswerTA> {
	@Override
	default void customize(QuerydslBindings bindings, QFormAnswerTA root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}
}
