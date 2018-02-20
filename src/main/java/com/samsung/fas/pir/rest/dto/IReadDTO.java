package com.samsung.fas.pir.rest.dto;

public interface IReadDTO<TEntity, TDTO> {
	TDTO toDTO(TEntity entity);
}