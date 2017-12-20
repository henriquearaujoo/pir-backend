package com.samsung.fas.pir.utils.converters;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.util.UUID;

@Configuration
public class StringToUUID implements Converter<String, UUID> {
	@Override
	public UUID convert(String source) throws RESTRuntimeException {
		try {
			return IDCoder.decodeUUID(source);
		} catch (Exception e) {
			throw new RESTRuntimeException("id.invalid");
		}
	}
}