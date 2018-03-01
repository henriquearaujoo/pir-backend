package com.samsung.fas.pir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableAsync
@EnableSwagger2
public class PIRApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(PIRApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PIRApplication.class);
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(Controller.class))
				.paths(PathSelectors.ant("/rest/**"))
				.build()
				.apiInfo(new ApiInfo(
						"PIR REST Documentation",
						"Documentation for PIR REST Services",
						"0.0.7",
						"Terms of Service: TODO",
						new Contact("Instituto ITN", "http://www.institutoitn.com.br", "contato@institutoitn.com.br"),
						"Apache License Version 2.0",
						"https://www.apache.org/licenses/LICENSE-2.0",
						Collections.singleton(new VendorExtension<String>() {
							@Override
							public String getName() {
								return "Intituto de Tecnologia e Negócios do Norte";
							}

							@Override
							public String getValue() {
								return "Instituto ITN";
							}
						})
						));
	}
}