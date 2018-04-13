package com.samsung.fas.pir.rest.utils;

import com.samsung.fas.pir.exception.RESTException;
import org.apache.tomcat.util.codec.binary.Base64;

import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.UUID;

public class IDCoder {
	public static String encode(UUID id) {
		UUID		uuid	= Optional.ofNullable(id).orElseThrow(() -> new RESTException("internal.error"));
		ByteBuffer	buffer	= ByteBuffer.wrap(new byte[16]);
		buffer.putLong(uuid.getMostSignificantBits());
		buffer.putLong(uuid.getLeastSignificantBits());
		return Base64.encodeBase64URLSafeString(buffer.array());
	}

	public static UUID decode(String base) throws RESTException {
		String		todecode	= Optional.ofNullable(base != null && !base.trim().isEmpty()? base : null).orElse(null);
		ByteBuffer 	buffer 		= todecode != null? ByteBuffer.wrap(Base64.decodeBase64(todecode)) : null;
		return buffer != null? new UUID(buffer.getLong(), buffer.getLong()) : null;
	}
}