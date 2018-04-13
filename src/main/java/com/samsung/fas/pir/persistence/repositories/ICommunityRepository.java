package com.samsung.fas.pir.persistence.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.Community;
import com.samsung.fas.pir.persistence.models.QCommunity;
import com.samsung.fas.pir.persistence.repositories.base.BRepository;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

@Repository
public interface ICommunityRepository extends BRepository<Community, Long, QCommunity> {
	Community findOneByNameIgnoreCaseAndCityId(String name, long id);

	@Override
	default void customize(@Nonnull QuerydslBindings bindings, @Nonnull QCommunity root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}
}
