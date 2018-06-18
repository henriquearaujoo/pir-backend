package com.samsung.fas.pir.rest.services.base;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

public interface IBaseBO<T, DTO, ID extends Serializable> {
	DTO findOne(ID id, Device device, UserDetails details);
	DTO findOne(UUID uuid, Device device, UserDetails details);

	Collection<DTO> findAll(Device device, UserDetails details);
	Collection<DTO> findAll(Predicate predicate, Device device, UserDetails details);

	Page<DTO> findAll(Predicate predicate, Pageable pageable, Device device, UserDetails details);
	Page<DTO> findAll(Pageable pageable, Device device, UserDetails details);

	ID delete(UUID id);
	void delete(T entity);
	void delete(ID id);

	DTO save(DTO create, Device device, UserDetails details);
	DTO update(DTO update, Device device, UserDetails details);

	Collection<DTO> save(Collection<DTO> create, Device device, UserDetails details);
	Collection<DTO> update(Collection<DTO> update, Device device, UserDetails details);
}
