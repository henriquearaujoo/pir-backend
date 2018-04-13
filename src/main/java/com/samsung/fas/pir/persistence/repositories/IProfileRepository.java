package com.samsung.fas.pir.persistence.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.Profile;
import com.samsung.fas.pir.persistence.models.QProfile;
import com.samsung.fas.pir.persistence.repositories.base.BRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.List;

@Repository
public interface IProfileRepository extends BRepository<Profile, Long, QProfile> {
	@Query(value = "select * from profile order by lower(title)", nativeQuery = true)
	List<Profile> findAll();

	@Override
	default void customize(@Nonnull QuerydslBindings bindings, @Nonnull QProfile root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.uuid).as("id").withDefaultBinding();
		bindings.bind(root.active).as("status").withDefaultBinding();
	}
}
