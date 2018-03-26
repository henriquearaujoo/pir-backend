package com.samsung.fas.pir.persistence.repositories.base;

import com.querydsl.core.types.EntityPath;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface BRepository<TEntity, TPK extends Serializable, TQuery extends EntityPath<TEntity>> extends PagingAndSortingRepository<TEntity, TPK>, QuerydslPredicateExecutor<TEntity>, QuerydslBinderCustomizer<TQuery> {
	Optional<TEntity> findByUuid(UUID uuid);

	@Transactional
	Optional<TPK> deleteByUuid(UUID uuid);
}
