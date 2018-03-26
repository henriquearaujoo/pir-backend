package com.samsung.fas.pir.persistence.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.FormQuestion;
import com.samsung.fas.pir.persistence.models.QFormQuestion;
import com.samsung.fas.pir.persistence.repositories.base.BRepository;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

@Repository
public interface IFormQuestionRepository extends BRepository<FormQuestion, Long, QFormQuestion> {
	@Override
	default void customize(@Nonnull QuerydslBindings bindings, @Nonnull QFormQuestion root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}
}
