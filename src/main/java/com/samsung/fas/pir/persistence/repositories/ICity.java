package com.samsung.fas.pir.persistence.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.City;
import com.samsung.fas.pir.persistence.models.QCity;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.UUID;

@Repository
public interface ICity extends IBaseRepository<City, Long, QCity> {
	Collection<City> findAllByUuidIn(Collection<UUID> collection);
	City findByNameAndStateAbbreviation(String name, String abbreviation);

	@Override
	default void customize(@Nonnull QuerydslBindings bindings, @Nonnull QCity root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.bind(root.uuid).as("id").withDefaultBinding();
	}
}