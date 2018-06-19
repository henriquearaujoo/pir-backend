package com.samsung.fas.pir.persistence.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.QSAnswer;
import com.samsung.fas.pir.persistence.models.QSurveyQuestion;
import com.samsung.fas.pir.persistence.models.SAnswer;
import com.samsung.fas.pir.persistence.models.SurveyQuestion;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

@Repository
public interface ISurveyQuestion extends IBaseRepository<SurveyQuestion, Long, QSurveyQuestion> {
	@Override
	default void customize(@Nonnull QuerydslBindings bindings, @Nonnull QSurveyQuestion root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}
}
