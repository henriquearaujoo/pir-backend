package com.samsung.fas.pir.utils;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class IDCoder {
	public static String encode(UUID uuid) {
		return Base64Utils.encodeToUrlSafeString(uuid.toString().getBytes());
	}

	public static UUID decode(String string) throws RESTRuntimeException {
		try {
			return UUID.fromString(new String(Base64Utils.decodeFromUrlSafeString(string), StandardCharsets.UTF_8));
		} catch (Exception e) {
			throw new RESTRuntimeException("id.invalid");
		}
	}
}
