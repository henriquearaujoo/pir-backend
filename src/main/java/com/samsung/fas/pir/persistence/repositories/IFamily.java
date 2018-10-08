package com.samsung.fas.pir.persistence.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.Family;
import com.samsung.fas.pir.persistence.models.QFamily;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.UUID;

@Repository
public interface IFamily extends IBaseRepository<Family, Long, QFamily> {
	Collection<Family> findAllByAgentUuid(UUID uuid);
	Family findByAgentUuidAndExternalID(UUID uuid, long externalID);
	long countAllByCodeStartingWith(String prefix);

	@Override
	default void customize(@Nonnull QuerydslBindings bindings, @Nonnull QFamily root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.uuid).as("id").withDefaultBinding();
	}
}