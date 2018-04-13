package com.samsung.fas.pir.configuration;

import com.samsung.fas.pir.exception.RESTException;
import com.samsung.fas.pir.rest.utils.IDCoder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;
@Configuration
public class StringToUUID implements Converter<String, UUID> {
	@Override
	public UUID convert(@Nonnull String source) {
		return Optional.ofNullable(IDCoder.decode(source)).orElseThrow(() -> new RESTException("id.invalid"));
	}
}
