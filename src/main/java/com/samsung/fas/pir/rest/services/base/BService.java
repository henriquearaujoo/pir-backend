package com.samsung.fas.pir.rest.services.base;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.base.IBaseDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class BService<TEntity, TDTO, TDAO extends IBaseDAO<TEntity, TPK>, TPK extends Serializable> {
	protected	final	TDAO			dao;
	private 	final 	Class<TDTO>		readClass;
	private 	final 	Class<TEntity>	entityClass;

	public BService(TDAO dao, Class<TEntity> entityClass, Class<TDTO> readClass) {
		this.dao 			= dao;
		this.readClass		= readClass;
		this.entityClass	= entityClass;
	}

	public TDTO findOne(TPK id, UserDetails details) {
		return toDTO(dao.findOne(id), true);
	}

	public TDTO findOne(UUID uuid, UserDetails details) {
		return toDTO(dao.findOne(uuid), true);
	}

	public Collection<TDTO> findAll(UserDetails details) {
		return dao.findAll().stream().map(item -> toDTO(item, false)).collect(Collectors.toSet());
	}

	public Collection<TDTO> findAll(Predicate predicate, UserDetails details) {
		return dao.findAll(predicate).stream().map(item -> toDTO(item, false)).collect(Collectors.toSet());
	}

	public Page<TDTO> findAll(Predicate predicate, Pageable pageable, UserDetails details) {
		return dao.findAll(predicate, pageable).map(item -> toDTO(item, false));
	}

	public Page<TDTO> findAll(Pageable pageable, UserDetails details) {
		return dao.findAll(pageable).map(item -> toDTO(item, false));
	}

	public TPK delete(UUID id) {
		return dao.delete(id);
	}

	public void delete(TEntity entity) {
		dao.delete(entity);
	}

	public void delete(TPK id) {
		dao.delete(id);
	}

	public abstract TDTO save(TDTO create, UserDetails account);
	public abstract TDTO update(TDTO update, UserDetails account);

	//================================================================================================================//
	private TDTO toDTO(TEntity item, boolean detailed) {
		try {
			return readClass.getConstructor(entityClass, boolean.class).newInstance(item, detailed);
		} catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(readClass.getName() + ": No constructor matches (TEntity, Boolean)");
		}
	}
}


