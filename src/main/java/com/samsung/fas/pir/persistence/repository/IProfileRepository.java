package com.samsung.fas.pir.persistence.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.entity.Profile;
import com.samsung.fas.pir.persistence.models.entity.QProfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IProfileRepository extends PagingAndSortingRepository<Profile, UUID>, QueryDslPredicateExecutor<Profile>, QuerydslBinderCustomizer<QProfile> {
	@Query(value = "select * from profile order by lower(title)", nativeQuery = true)
	List<Profile> findAll();

	Profile findByTitleIgnoreCase(String title);
	Profile findOneByUuid(UUID guid);

	@Override
	default void customize(QuerydslBindings bindings, QProfile root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.uuid).as("id").withDefaultBinding();
		bindings.bind(root.active).as("status").withDefaultBinding();
	}
}
