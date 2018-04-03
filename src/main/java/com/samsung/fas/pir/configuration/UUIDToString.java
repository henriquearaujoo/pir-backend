package com.samsung.fas.pir.configuration;

import com.samsung.fas.pir.rest.utils.IDCoder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import javax.annotation.Nonnull;
import java.util.UUID;

@Configuration
public class UUIDToString implements Converter<UUID, String> {
	@Override
	public String convert(@Nonnull UUID source) {
		return IDCoder.encode(source);
	}
}
