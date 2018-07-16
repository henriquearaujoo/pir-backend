package com.samsung.fas.pir.configuration;

import com.samsung.fas.pir.persistence.models.base.Base;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Mapper {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper		mapper		= new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		mapper.getConfiguration().setSkipNullEnabled(true);
		return mapper;
	}
}