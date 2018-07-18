package com.samsung.fas.pir.persistence.repositories.base;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.samsung.fas.pir.persistence.models.base.QBase;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.annotation.Nonnull;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface IBaseRepository<T, ID extends Serializable, TQ extends EntityPath<T>> extends PagingAndSortingRepository<T, ID>, QuerydslPredicateExecutor<T>, QuerydslBinderCustomizer<TQ> {
	Optional<T> findByUuid(UUID uuid);
	@Transactional
	Optional<ID> deleteByUuid(UUID uuid);
}