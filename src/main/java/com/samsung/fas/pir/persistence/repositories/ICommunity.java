package com.samsung.fas.pir.persistence.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.Community;
import com.samsung.fas.pir.persistence.models.QCommunity;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.UUID;

@Repository
public interface ICommunity extends IBaseRepository<Community, Long, QCommunity> {
	Community findOneByNameIgnoreCaseAndCityId(String name, long id);
	Community findOneByNameIgnoreCaseAndCityUuid(String name, UUID id);
	Collection<Community> findAllByUuidIn(Collection<UUID> collection);

	@Override
	default void customize(@Nonnull QuerydslBindings bindings, @Nonnull QCommunity root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.uuid).as("id").withDefaultBinding();
	}
}
