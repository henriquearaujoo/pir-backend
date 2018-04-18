package com.samsung.fas.pir.persistence.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.FormAnswerTA;
import com.samsung.fas.pir.persistence.models.QFormAnswerTA;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

@Repository
public interface IFormAnswerTA extends IBaseRepository<FormAnswerTA, Long, QFormAnswerTA> {
	@Override
	default void customize(@Nonnull QuerydslBindings bindings, @Nonnull QFormAnswerTA root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}
}
