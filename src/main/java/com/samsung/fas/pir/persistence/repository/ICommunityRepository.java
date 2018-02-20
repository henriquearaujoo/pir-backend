package com.samsung.fas.pir.persistence.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.entity.Community;
import com.samsung.fas.pir.persistence.models.entity.QCommunity;
import com.samsung.fas.pir.persistence.repository.base.BRepository;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommunityRepository extends BRepository<Community, Long, QCommunity> {
	Community findOneByNameIgnoreCaseAndCityId(String name, long id);

	@Override
	default void customize(QuerydslBindings bindings, QCommunity root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}
}
