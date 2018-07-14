package com.samsung.fas.pir.rest.dto.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.samsung.fas.pir.configuration.MapperHolder;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.UUID;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public abstract class BaseDTO<T> implements IBaseDTO<T> {
	@JsonIgnore
	@Getter(value = AccessLevel.PROTECTED)
	@Setter(value = AccessLevel.PRIVATE)
	private 		ModelMapper			mapper;

	@Getter
	@Setter
	@JsonProperty("id")
	private 		UUID 				uuid;

	public BaseDTO() {
		setMapper(MapperHolder.getMapper());
	}

	public BaseDTO(T entity) {
		setMapper(MapperHolder.getMapper());
		getMapper().map(entity, this);
	}

	@SuppressWarnings("unchecked")
	@JsonIgnore
	@Override
	public T getModel() {
		try {
			T model = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
			getMapper().map(this, model);
			return model;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
}

