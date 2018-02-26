package com.samsung.fas.pir.rest.services.base;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.dao.base.IBaseDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class BService<TEntity, TCreate, TRead, TUpdate, TDAO extends IBaseDAO<TEntity, TPK>, TPK extends Serializable> {
	protected  	TDAO			dao;
	private 	Class<TRead>	readClass;
	private 	Class<TEntity>	entityClass;

	public BService(TDAO dao, Class<TEntity> entityClass, Class<TRead> readClass) {
		this.dao 			= dao;
		this.readClass		= readClass;
		this.entityClass	= entityClass;
	}

	public TRead findOne(TPK id) {
		return toDTO(Optional.ofNullable(dao.findOne(id)).orElseThrow(() -> new RESTRuntimeException("notfound")));
	}

	public TRead findOne(UUID uuid) {
		return toDTO(Optional.ofNullable(dao.findOne(uuid)).orElseThrow(() -> new RESTRuntimeException("notfound")));
	}

	public Collection<TRead> findAll() {
		return dao.findAll().stream().map(this::toDTO).collect(Collectors.toSet());
	}

	public Collection<TRead> findAll(Predicate predicate) {
		return dao.findAll(predicate).stream().map(this::toDTO).collect(Collectors.toSet());
	}

	public Page<TRead> findAll(Predicate predicate, Pageable pageable) {
		return dao.findAll(predicate, pageable).map(this::toDTO);
	}

	public Page<TRead> findAll(Pageable pageable) {
		return dao.findAll(pageable).map(this::toDTO);
	}

	public abstract TRead save(TCreate create, Account account);
	public abstract TRead update(TUpdate update, Account account);

	//================================================================================================================//
	private TRead toDTO(TEntity item) {
		try {
			return readClass.getConstructor(entityClass).newInstance(item);
		} catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(readClass.getName() + ": No constructor matches (TEntity)");
		}
	}
}


