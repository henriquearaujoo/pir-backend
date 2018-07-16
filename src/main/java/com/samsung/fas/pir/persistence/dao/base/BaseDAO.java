package com.samsung.fas.pir.persistence.dao.base;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.ServiceException;
import com.samsung.fas.pir.persistence.models.base.Base;
import com.samsung.fas.pir.persistence.repositories.base.IBaseRepository;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@SuppressWarnings("unchecked")
public abstract class BaseDAO<T, ID extends Serializable, TR extends IBaseRepository<T, ID, TQ>, TQ extends EntityPath<T>> implements IBaseDAO<T, ID> {
	@Getter(AccessLevel.PROTECTED)
	private		final	TR			repository;

	@Getter
	private 	final	String		className;

	@Autowired
	public BaseDAO(TR repository) {
		this.repository = repository;
		this.className	= ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName().toLowerCase();
	}

	@Override
	public T findOne(ID id) {
		return getRepository().findById(Optional.ofNullable(id).orElseThrow(() -> new ServiceException(className + ".id.missing"))).orElseThrow(() -> new ServiceException(className + ".not.found"));
	}

	@Override
	public T findOne(UUID uuid) {
		return getRepository().findByUuid(Optional.ofNullable(uuid).orElseThrow(() -> new ServiceException(className + ".id.missing"))).orElseThrow(() -> new ServiceException(className + ".not.found"));
	}

	@Override
	public Collection<T> findAll() {
		return StreamSupport.stream(getRepository().findAll(new Sort(Sort.Direction.ASC, "id")).spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public Collection<T> findAll(Predicate predicate) {
		return StreamSupport.stream(getRepository().findAll(predicate).spliterator(), false).sorted(Comparator.comparingLong(item -> ((Base) item).getId())).collect(Collectors.toList());
	}

	@Override
	public Page<T> findAll(Predicate predicate, Pageable pageable) {
		return getRepository().findAll(predicate, pageable);
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		return getRepository().findAll(pageable);
	}

	@Override
	public T save(T model) {
		return getRepository().save(model);
	}

	@Override
	public Collection<T> save(Iterable<T> models) {
		return StreamSupport.stream(getRepository().saveAll(models).spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public void delete(ID id) {
		getRepository().deleteById(id);
	}

	@Override
	public ID delete(UUID id) {
		return getRepository().deleteByUuid(id).orElseThrow(() -> new ServiceException("cannot.delete"));
	}

	@Override
	public void delete(T entity) {
		getRepository().delete(entity);
	}
}