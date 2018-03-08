package com.samsung.fas.pir.persistence.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.entity.QResponsible;
import com.samsung.fas.pir.persistence.models.entity.Responsible;
import com.samsung.fas.pir.persistence.repository.base.BRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

import java.util.Collection;

public interface IResponsibleRepository extends BRepository<Responsible, Long, QResponsible> {
	Collection<Responsible> findAllByMotherIsNull();
	Page<Responsible> findAllByMotherIsNull(Pageable pageable);

	@Override
	default void customize(QuerydslBindings bindings, QResponsible root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}
}
