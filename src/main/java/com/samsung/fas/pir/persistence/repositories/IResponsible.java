package com.samsung.fas.pir.persistence.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.QResponsible;
import com.samsung.fas.pir.persistence.models.Responsible;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.UUID;

@Repository
public interface IResponsible extends IBaseRepository<Responsible, Long, QResponsible> {
	Collection<Responsible> findAllByMobileIdIn(Collection<Long> collection);
	Collection<Responsible> findAllByUuidIn(Collection<UUID> collection);

	@Override
	default void customize(@Nonnull QuerydslBindings bindings, @Nonnull QResponsible root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}
}
