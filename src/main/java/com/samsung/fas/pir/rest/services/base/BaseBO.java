package com.samsung.fas.pir.rest.services.base;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.base.IBaseDAO;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;

import javax.inject.Inject;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class BaseBO<T, DAO extends IBaseDAO<T, ID>, DTO, ID extends Serializable> implements IBaseBO<T, DTO, ID> {
	@Getter(AccessLevel.PROTECTED)
	private 	final 	DAO			dao;
	private 	final 	Class<T>	entity;
	private 	final 	Class<DTO>	dto;

	@SuppressWarnings("unchecked")
	@Inject
	protected BaseBO(DAO dao) {
		entity		= (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		dto			= (Class<DTO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
		this.dao	= dao;
	}

	@Override
	public DTO findOne(ID id, Device device, UserDetails details) {
		return toDTO(getDao().findOne(id), device, true);
	}

	@Override
	public DTO findOne(UUID uuid, Device device, UserDetails details) {
		return toDTO(getDao().findOne(uuid), device, true);
	}

	@Override
	public Collection<DTO> findAll(Device device, UserDetails details) {
		return getDao().findAll().stream().map(item -> toDTO(item, device, false)).collect(Collectors.toList());
	}

	@Override
	public Collection<DTO> findAll(Predicate predicate,  Device device, UserDetails details) {
		return getDao().findAll(predicate).stream().map(item -> toDTO(item, device, false)).collect(Collectors.toList());
	}

	@Override
	public Page<DTO> findAll(Predicate predicate, Pageable pageable, Device device, UserDetails details) {
		return getDao().findAll(predicate, pageable).map(item -> toDTO(item, device, false));
	}

	@Override
	public Page<DTO> findAll(Pageable pageable, Device device, UserDetails details) {
		return getDao().findAll(pageable).map(item -> toDTO(item, device, false));
	}

	@Override
	public ID delete(UUID id) {
		return getDao().delete(id);
	}

	@Override
	public void delete(T entity) {
		getDao().delete(entity);
	}

	@Override
	public void delete(ID id) {
		getDao().delete(id);
	}

	private DTO toDTO(T item, Device device, boolean detailed) {
		try {
			return dto.getConstructor(entity, Device.class, boolean.class).newInstance(item, device, detailed);
		} catch (Exception e) {
			try {
				return dto.getConstructor(entity, boolean.class).newInstance(item, detailed);
			} catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
				ex.printStackTrace();
				throw new RuntimeException(dto.getName() + ": No constructor matches (T, Boolean) or (T, Device, Boolean");
			}
		}
	}
}


