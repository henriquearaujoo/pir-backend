package com.samsung.fas.pir.persistence.repository.base;

import com.querydsl.core.types.EntityPath;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.UUID;

@NoRepositoryBean
public interface BRepository<TEntity, TPK extends Serializable, TQuery extends EntityPath<TEntity>> extends PagingAndSortingRepository<TEntity, TPK>, QueryDslPredicateExecutor<TEntity>, QuerydslBinderCustomizer<TQuery> {
	TEntity findByUuid(UUID uuid);
}
