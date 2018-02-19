package com.samsung.fas.pir.utils;

import com.samsung.fas.pir.exception.RESTRuntimeException;
import org.apache.commons.codec.binary.Base64;

import java.nio.ByteBuffer;
import java.util.UUID;

public class IDCoder {
	public static String encode(UUID uuid) {
		ByteBuffer	buffer	= ByteBuffer.wrap(new byte[16]);
		buffer.putLong(uuid.getMostSignificantBits());
		buffer.putLong(uuid.getLeastSignificantBits());
		return Base64.encodeBase64URLSafeString(buffer.array());
	}

	public static UUID decode(String string) throws RESTRuntimeException {
		try {
			ByteBuffer buffer = ByteBuffer.wrap(Base64.decodeBase64(string));
			return new UUID(buffer.getLong(), buffer.getLong());
		} catch (Exception e) {
			throw new RESTRuntimeException("id.invalid");
		}
	}
}