package com.samsung.fas.pir.persistence.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.QSAnswer;
import com.samsung.fas.pir.persistence.models.QSurvey;
import com.samsung.fas.pir.persistence.models.SAnswer;
import com.samsung.fas.pir.persistence.models.Survey;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

@Repository
public interface ISAnswer extends IBaseRepository<SAnswer, Long, QSAnswer> {
	@Override
	default void customize(@Nonnull QuerydslBindings bindings, @Nonnull QSAnswer root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}
}
