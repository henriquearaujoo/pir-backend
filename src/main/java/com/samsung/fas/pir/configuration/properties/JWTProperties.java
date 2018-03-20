package com.samsung.fas.pir.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(value = "jwt", prefix = "jwt")
public class JWTProperties {
	@Getter
	@Setter
	private		String		secret;

	@Getter
	@Setter
	private		String		appName;

	@Getter
	@Setter
	private 	String		header;

	@Getter
	@Setter
	private 	long		expiresIn;

	@Getter
	@Setter
	private 	long		mobileExpiresIn;
}
