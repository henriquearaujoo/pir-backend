package com.samsung.fas.pir.bi.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class Methods {
	private		static			Logger			Log			= LoggerFactory.getLogger(Methods.class);

	public static <T> Consumer<T> handle(Consumer<T> consumer, String message) {
		return i -> {
			try {
				consumer.accept(i);
			} catch (Exception e) {
				Log.error(message != null? message : e.getMessage());
			}
		};
	}
}