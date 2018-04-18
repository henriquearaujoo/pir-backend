package com.samsung.fas.pir.persistence.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.QRule;
import com.samsung.fas.pir.persistence.models.Rule;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

@Repository
public interface IRulesRepository extends IBaseRepository<Rule, Long, QRule> {
	@Override
	default void customize(@Nonnull QuerydslBindings bindings, @Nonnull QRule root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.uuid).as("id").withDefaultBinding();
		bindings.bind(root.profile.uuid).as("profile").withDefaultBinding();
		bindings.bind(root.page.uuid).as("page").withDefaultBinding();
		bindings.excluding(root.profile.id, root.page.id);
	}
}