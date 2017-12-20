package com.samsung.fas.pir.utils;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class IDCoder {
	public static String encode(UUID uuid) {
		return Base64Utils.encodeToUrlSafeString(uuid.toString().getBytes());
	}

	public static String encode(long id) {
		return Base64Utils.encodeToUrlSafeString(String.valueOf(id).getBytes());
	}

	public static long decodeLong(String string) {
		try {
			return Long.parseLong(new String(Base64Utils.decodeFromUrlSafeString(string), StandardCharsets.UTF_8));
		} catch (Exception e) {
			throw new RESTRuntimeException("id.invalid");
		}
	}

	public static UUID decodeUUID(String string) throws RESTRuntimeException {
		try {
			return UUID.fromString(new String(Base64Utils.decodeFromUrlSafeString(string), StandardCharsets.UTF_8));
		} catch (Exception e) {
			throw new RESTRuntimeException("id.invalid");
		}
	}
}
