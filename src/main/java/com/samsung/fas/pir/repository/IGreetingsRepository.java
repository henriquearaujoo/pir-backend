package com.samsung.fas.pir.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.models.entity.Greetings;
import com.samsung.fas.pir.models.entity.QGreetings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGreetingsRepository extends JpaRepository<Greetings, Long>, PagingAndSortingRepository<Greetings, Long>, QueryDslPredicateExecutor<Greetings>, QuerydslBinderCustomizer<QGreetings> {

	@Override
	default void customize(QuerydslBindings bindings, QGreetings root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}
}
