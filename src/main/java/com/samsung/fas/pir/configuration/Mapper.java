package com.samsung.fas.pir.configuration;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Mapper {
	@Getter
	private		static		ModelMapper		mapper;

	@Autowired
	private void setMapper(ModelMapper mapper) {
		synchronized (this) {
			if (mapper != null) {
				Mapper.mapper = mapper;
			}
		}
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper		mapper		= new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		mapper.getConfiguration().setSkipNullEnabled(true);
		return mapper;
	}
}