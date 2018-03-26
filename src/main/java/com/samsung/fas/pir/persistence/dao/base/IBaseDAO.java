package com.samsung.fas.pir.persistence.dao.base;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

public interface IBaseDAO<TEntity, TPK extends Serializable>{
	TEntity findOne(TPK id);
	TEntity findOne(UUID uuid);

	Collection<TEntity> findAll();
	Collection<TEntity> findAll(Predicate predicate);

	Page<TEntity> findAll(Predicate predicate, Pageable pageable);
	Page<TEntity> findAll(Pageable pageable);

	TEntity save(TEntity model);
	Collection<TEntity> save(Iterable<TEntity> models);

	TPK delete(UUID id);
	void delete(TPK id);
	void delete(TEntity entity);
}