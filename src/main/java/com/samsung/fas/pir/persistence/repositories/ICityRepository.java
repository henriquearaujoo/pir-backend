package com.samsung.fas.pir.persistence.repositories;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.City;
import com.samsung.fas.pir.persistence.models.QCity;
import com.samsung.fas.pir.persistence.repositories.base.BRepository;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.List;

@Repository
public interface ICityRepository extends BRepository<City, Long, QCity> {
	List<City> findByStateId(long id);

	@Override
	default void customize(@Nonnull QuerydslBindings bindings, @Nonnull QCity root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}
}