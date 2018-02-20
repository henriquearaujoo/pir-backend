package com.samsung.fas.pir.rest.services.base;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.persistence.dao.base.IBaseDAO;
import com.samsung.fas.pir.rest.dto.IReadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class BService<TEntity, TCreate, TRead extends IReadDTO<TEntity, TRead>, TUpdate, TDAO extends IBaseDAO<TEntity, TPK>, TPK extends Serializable> {
	protected  	TDAO	dao;
	private		TRead	dto;

	public BService(TDAO dao, Class<TRead> clazz) {
		this.dao 	= dao;
		try {
			this.dto	= clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public TRead findOne(TPK id) {
		return dto.toDTO(dao.findOne(id));
	}
	public TRead findOne(UUID uuid) {
		return dto.toDTO(dao.findOne(uuid));
	}

	public Collection<TRead> findAll() {
		return dao.findAll().stream().map(item -> dto.toDTO(item)).collect(Collectors.toSet());
	}
	public Collection<TRead> findAll(Predicate predicate) {
		return dao.findAll(predicate).stream().map(item -> dto.toDTO(item)).collect(Collectors.toSet());
	}

	public Page<TRead> findAll(Predicate predicate, Pageable pageable) {
		return dao.findAll(predicate, pageable).map(item -> dto.toDTO(item));
	}

	public Page<TRead> findAll(Pageable pageable) {
		return dao.findAll(pageable).map(item -> dto.toDTO(item));
	}

	public abstract TRead save(TCreate create, Account account);
	public abstract TRead update(TUpdate update, Account account);
}

