package com.samsung.fas.pir.persistence.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.Alternative;
import com.samsung.fas.pir.persistence.models.QAlternative;
import com.samsung.fas.pir.persistence.models.QAnswer;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

@Repository
public interface IAlternative extends IBaseRepository<Alternative, Long, QAlternative> {
	@Override
	default void customize(@Nonnull QuerydslBindings bindings, @Nonnull QAlternative root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.uuid).as("id").withDefaultBinding();
		bindings.bind(root.question.uuid).as("question").withDefaultBinding();
	}
}
