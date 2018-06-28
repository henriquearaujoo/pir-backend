package com.samsung.fas.pir;

import com.samsung.fas.pir.configuration.properties.FCMProperties;
import com.samsung.fas.pir.configuration.properties.JWTProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableAsync
@EnableScheduling
@EnableCaching
@EnableConfigurationProperties(value = {FCMProperties.class, JWTProperties.class})
@SpringBootApplication
public class PIRApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(PIRApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PIRApplication.class);
	}
}
