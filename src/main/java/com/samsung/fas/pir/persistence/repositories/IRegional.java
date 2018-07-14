package com.samsung.fas.pir.persistence.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.QRegional;
import com.samsung.fas.pir.persistence.models.Regional;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

@Repository
public interface IRegional extends IBaseRepository<Regional, Long, QRegional> {
	@Override
	default void customize(@Nonnull QuerydslBindings bindings, @Nonnull QRegional root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}
}