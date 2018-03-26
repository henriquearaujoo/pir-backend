package com.samsung.fas.pir.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "fcm", prefix = "fcm")
public class FCMProperties {
	@Getter
	@Setter
	private		String		serverUrl;

	@Getter
	@Setter
	private		String		serverKey;
}
