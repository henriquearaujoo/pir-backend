package com.samsung.fas.pir.persistence.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.entity.City;
import com.samsung.fas.pir.persistence.models.entity.QCity;
import com.samsung.fas.pir.persistence.repository.base.BRepository;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICityRepository extends BRepository<City, Long, QCity> {
	List<City> findByStateId(long id);

	@Override
	default void customize(QuerydslBindings bindings, QCity root) {
		bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}
}