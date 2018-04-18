package com.samsung.fas.pir.rest.services.base;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

public interface IBaseBO<T, DTO, ID extends Serializable> {
	DTO findOne(ID id, UserDetails details);
	DTO findOne(UUID uuid, UserDetails details);

	Collection<DTO> findAll(UserDetails details);
	Collection<DTO> findAll(Predicate predicate, UserDetails details);

	Page<DTO> findAll(Predicate predicate, Pageable pageable, UserDetails details);
	Page<DTO> findAll(Pageable pageable, UserDetails details);

	ID delete(UUID id);
	void delete(T entity);
	void delete(ID id);

	DTO save(DTO create, UserDetails account);
	DTO update(DTO update, UserDetails account);
}
