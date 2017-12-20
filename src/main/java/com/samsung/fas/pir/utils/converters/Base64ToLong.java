package com.samsung.fas.pir.utils.converters;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import com.samsung.fas.pir.utils.IDCoder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class Base64ToLong implements Converter<String, Long> {
	@Override
	public Long convert(String source) throws RESTRuntimeException {
		try {
			return IDCoder.decodeLong(source);
		} catch (Exception e) {
			throw new RESTRuntimeException("id.invalid");
		}
	}
}