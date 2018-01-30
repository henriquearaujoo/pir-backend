package com.samsung.fas.pir.persistence.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.entity.LegalPerson;
import com.samsung.fas.pir.persistence.models.entity.QLegalPerson;
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
import java.util.List;

@Repository
public interface ILegalPersonRepository extends JpaRepository<LegalPerson, Long>, PagingAndSortingRepository<LegalPerson, Long>, QueryDslPredicateExecutor<LegalPerson>, QuerydslBinderCustomizer<QLegalPerson> {
	LegalPerson findByCnpj(String cnpj);

	List<LegalPerson> findByIe(String ie);


	@Modifying
	@Transactional
	@Query(value = "delete from legal_person where id not in (SELECT entity_fk FROM \"user\" WHERE entity_fk IS NOT NULL)", nativeQuery = true)
	void trim();

	@Override
	default void customize(QuerydslBindings bindings, QLegalPerson root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}
}
