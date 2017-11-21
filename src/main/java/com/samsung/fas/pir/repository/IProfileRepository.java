package com.samsung.fas.pir.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.models.entity.Profile;
import com.samsung.fas.pir.models.entity.QProfile;
import org.springframework.data.jpa.repository.JpaRepository;
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
public interface IProfileRepository extends JpaRepository<Profile, UUID>, PagingAndSortingRepository<Profile, UUID>, QueryDslPredicateExecutor<Profile>, QuerydslBinderCustomizer<QProfile> {
	@Query(value = "select * from profile order by lower(title)", nativeQuery = true)
	List<Profile> findAll();

	Profile findByTitleIgnoreCase(String title);
	Profile findOneByGuid(UUID guid);

	@Override
	default void customize(QuerydslBindings bindings, QProfile root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.guid).as("id").withDefaultBinding();
		bindings.bind(root.active).as("status").withDefaultBinding();
		bindings.bind(root.title).as("title").withDefaultBinding();
	}
}
