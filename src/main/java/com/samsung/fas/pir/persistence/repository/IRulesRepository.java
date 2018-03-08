package com.samsung.fas.pir.persistence.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.entity.QRule;
import com.samsung.fas.pir.persistence.models.entity.Rule;
import com.samsung.fas.pir.persistence.repository.base.BRepository;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface IRulesRepository extends BRepository<Rule, Long, QRule> {
	@Override
	default void customize(QuerydslBindings bindings, QRule root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.uuid).as("id").withDefaultBinding();
		bindings.bind(root.profile.uuid).as("profile").withDefaultBinding();
		bindings.bind(root.page.uuid).as("page").withDefaultBinding();
		bindings.excluding(root.profile.id, root.page.id);
	}
}