package com.samsung.fas.pir.persistence.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.entity.Answer;
import com.samsung.fas.pir.persistence.models.entity.QAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAnswerRepository extends JpaRepository<Answer, Long>, PagingAndSortingRepository<Answer, Long>, QueryDslPredicateExecutor<Answer>, QuerydslBinderCustomizer<QAnswer> {
	Answer findByUuid(UUID uuid);

	@Override
	default void customize(QuerydslBindings bindings, QAnswer root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.uuid).as("id").withDefaultBinding();
		bindings.bind(root.question.uuid).as("question").withDefaultBinding();
	}
}
