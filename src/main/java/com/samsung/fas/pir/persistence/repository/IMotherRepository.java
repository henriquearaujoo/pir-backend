package com.samsung.fas.pir.persistence.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.entity.Mother;
import com.samsung.fas.pir.persistence.models.entity.QMother;
import com.samsung.fas.pir.persistence.repository.base.BRepository;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface IMotherRepository extends BRepository<Mother, Long, QMother> {
	@Override
	default void customize(QuerydslBindings bindings, QMother root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.responsible.name).as("mother.name").withDefaultBinding();
	}
}
