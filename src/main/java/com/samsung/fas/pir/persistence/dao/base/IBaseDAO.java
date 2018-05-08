package com.samsung.fas.pir.persistence.dao.base;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

public interface IBaseDAO<T, ID extends Serializable>{
	T findOne(ID id);
	T findOne(UUID uuid);

	Collection<T> findAll();
	Collection<T> findAll(Predicate predicate);

	Page<T> findAll(Predicate predicate, Pageable pageable);
	Page<T> findAll(Pageable pageable);

	T save(T model);
	Collection<T> save(Iterable<T> models);

	ID delete(UUID id);
	void delete(ID id);
	void delete(T entity);
}