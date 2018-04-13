package com.samsung.fas.pir.persistence.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.Form;
import com.samsung.fas.pir.persistence.models.QForm;
import com.samsung.fas.pir.persistence.repositories.base.BRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.transaction.Transactional;
import java.util.Collection;

@Repository
public interface IFormRepository extends BRepository<Form, Long, QForm> {
	Collection<Form> findAllByAgeZone(int zone);

	@Transactional
	@Modifying
	@Query(value = "update forms set enabled = false where age_zone = ?1", nativeQuery = true)
	void invalidateAll(int zone);

	@Override
	default void customize(@Nonnull QuerydslBindings bindings, @Nonnull QForm root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.ageZone).as("age_zone").withDefaultBinding();
	}
}
