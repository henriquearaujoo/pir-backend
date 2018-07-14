package com.samsung.fas.pir.configuration;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MapperHolder {
	@Getter
	private		static		ModelMapper		mapper;

	@Autowired
	public void setMapper(ModelMapper mapper) {
		synchronized (this) {
			if (mapper != null) {
				MapperHolder.mapper = mapper;
			}
		}
	}
}