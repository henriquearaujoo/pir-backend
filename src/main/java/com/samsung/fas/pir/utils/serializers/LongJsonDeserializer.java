package com.samsung.fas.pir.utils.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.samsung.fas.pir.exception.RESTRuntimeException;

import java.io.IOException;

public class LongJsonDeserializer extends JsonDeserializer<Long> {
	@Override
	public Long deserialize(JsonParser parser, DeserializationContext context) throws IOException, RESTRuntimeException {
		try {
			return parser.getLongValue();
		} catch (Exception e) {
			throw new RESTRuntimeException("invalid.number.format");
		}
	}
}