package com.samsung.fas.pir.persistence.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.Child;
import com.samsung.fas.pir.persistence.models.QChild;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.UUID;

@Repository
public interface IChild extends IBaseRepository<Child, Long, QChild> {
	Collection<Child> findAllByUuidIn(Collection<UUID> collection);
	Child findByAgentUuidAndExternalID(UUID uuid, long externalID);
	long countAllByCodeStartingWith(String prefix);

	@Override
	default void customize(@Nonnull QuerydslBindings bindings, @Nonnull QChild root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.uuid).as("id").withDefaultBinding();
	}
}
