package com.samsung.fas.pir.persistence.dao.base;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.repository.base.BRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class BaseDAO<TEntity, TPK extends Serializable, TQuery extends EntityPath<TEntity>> implements IBaseDAO<TEntity, TPK> {
	@Inject
	protected BRepository<TEntity, TPK, TQuery> repository;

	public TEntity findOne(TPK id) {
		return repository.findOne(id);
	}

	@Override
	public TEntity findOne(UUID uuid) {
		return repository.findByUuid(uuid);
	}

	public Set<TEntity> findAll() {
		return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toSet());
	}

	public Set<TEntity> findAll(Predicate predicate) {
		return StreamSupport.stream(repository.findAll(predicate).spliterator(), false).collect(Collectors.toSet());
	}

	public Page<TEntity> findAll(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}

	public Page<TEntity> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public TEntity save(TEntity model) {
		return repository.save(model);
	}

	public Set<TEntity> save(Iterable<TEntity> models) {
		return StreamSupport.stream(repository.save(models).spliterator(), false).collect(Collectors.toSet());
	}

	public void delete(TPK id) {
		repository.delete(id);
	}

	public TPK delete(UUID id) {
		return repository.deleteByUuid(id);
	}

	public void delete(TEntity entity) {
		repository.delete(entity);
	}
}