package com.samsung.fas.pir.persistence.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.entity.QRule;
import com.samsung.fas.pir.persistence.models.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface IRulesRepository extends JpaRepository<Rule, UUID>, PagingAndSortingRepository<Rule, UUID>, QueryDslPredicateExecutor<Rule>, QuerydslBinderCustomizer<QRule> {
	List<Rule> findByProfileGuid(UUID id);
	Rule findByProfileIdAndPageId(long profile, UUID page);
	Rule findOneByGuid(UUID id);
	@Transactional
	void deleteByGuid(UUID id);

	@Override
	default void customize(QuerydslBindings bindings, QRule root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.guid).as("id").withDefaultBinding();
		bindings.bind(root.profile.guid).as("profile").withDefaultBinding();
		bindings.bind(root.page.guid).as("page").withDefaultBinding();
		bindings.excluding(root.profile.id, root.page.id);
	}
}