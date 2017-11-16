package com.samsung.fas.pir.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.util.UUID;

@Configuration
public class StringToUUIDConverter implements Converter<String, UUID> {
	@Override
	public UUID convert(String coded) {
		return IDCoder.decode(coded);
	}
}
