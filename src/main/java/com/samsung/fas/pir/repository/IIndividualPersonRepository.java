package com.samsung.fas.pir.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.models.entity.IndividualPerson;
import com.samsung.fas.pir.models.entity.QIndividualPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IIndividualPersonRepository extends JpaRepository<IndividualPerson, Long>, PagingAndSortingRepository<IndividualPerson, Long>, QueryDslPredicateExecutor<IndividualPerson>, QuerydslBinderCustomizer<QIndividualPerson> {

	@Modifying
	@Transactional
	@Query(value = "delete from individual_person where id not in (SELECT individual_fk FROM \"user\" WHERE individual_fk IS NOT NULL)", nativeQuery = true)
	void trim();


	@Override
	default void customize(QuerydslBindings bindings, QIndividualPerson root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}
}
