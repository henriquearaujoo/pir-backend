package com.samsung.fas.pir.utils.converters;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class StringLongToDate implements Converter<String, Date> {
	@Override
	public Date convert(String source) {
		try {
			return new Date(Long.parseLong(source));
		} catch (Exception e) {
			try {
				return new SimpleDateFormat("dd-MM-yyyy").parse(source);
			} catch (ParseException pe) {
				throw new RESTRuntimeException("date.not.recognized");
			}
		}
	}
}
