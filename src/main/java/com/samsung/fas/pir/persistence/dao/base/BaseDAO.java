package com.samsung.fas.pir.persistence.dao.base;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTException;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class BaseDAO<T, ID extends Serializable, TR extends IBaseRepository<T, ID, TQ>, TQ extends EntityPath<T>> implements IBaseDAO<T, ID> {
	@Getter(AccessLevel.PROTECTED)
	private	final	TR	repository;

	@Autowired
	public BaseDAO(TR repository) {
		this.repository = repository;
	}

	@Override
	public T findOne(ID id) {
		return repository.findById(Optional.ofNullable(id).orElseThrow(() -> new RESTException("id.missing"))).orElseThrow(() -> new RESTException("not.found"));
	}

	@Override
	public T findOne(UUID uuid) {
		return repository.findByUuid(Optional.ofNullable(uuid).orElseThrow(() -> new RESTException("id.missing"))).orElseThrow(() -> new RESTException("not.found"));
	}

	@Override
	public Set<T> findAll() {
		return StreamSupport.stream(repository.findAll(new Sort(Sort.Direction.ASC, "id")).spliterator(), false).collect(Collectors.toSet());
	}

	@Override
	public Set<T> findAll(Predicate predicate) {
		return StreamSupport.stream(repository.findAll(predicate).spliterator(), false).collect(Collectors.toSet());
	}

	@Override
	public Page<T> findAll(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public T save(T model) {
		return repository.save(model);
	}

	@Override
	public List<T> save(Iterable<T> models) {
		return StreamSupport.stream(repository.saveAll(models).spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public void delete(ID id) {
		repository.deleteById(id);
	}

	@Override
	public ID delete(UUID id) {
		return repository.deleteByUuid(id).orElseThrow(() -> new RESTException("cannot.delete"));
	}

	@Override
	public void delete(T entity) {
		repository.delete(entity);
	}
}