package com.samsung.fas.pir.configuration;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

@Configuration
public class Swagger {
	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
					   .select()
					   .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
					   .paths(PathSelectors.ant("/rest/**"))
					   .build()
					   .pathMapping("/")
					   .securitySchemes(Collections.singletonList(new ApiKey("APIKEY", HttpHeaders.AUTHORIZATION, "HEADER")))
					   .securityContexts(Collections.singletonList(SecurityContext.builder()
																		   .securityReferences(authorization())
																		   .forPaths(PathSelectors.ant("/rest/**"))
																		   .forPaths(Predicates.not(PathSelectors.ant("/rest/authentication/**")))
																		   .forPaths(Predicates.not(PathSelectors.ant("/rest/firebase/**")))
																		   .build()))
					   .apiInfo(new ApiInfo(
							   "PIR REST Documentation",
							   "Documentation for PIR REST Services",
							   "DEVELOPMENT 0.0.10",
							   "Terms of Service: TODO",
							   new Contact("Instituto ITN", "http://www.institutoitn.com.br", "contato@institutoitn.com.br"),
							   "TO DEFINE",
							   "TO DEFINE",
							   Collections.singleton(new VendorExtension<String>() {
								   @Override
								   public String getName() {
									   return "Intituto de Tecnologia e Negócios do Norte";
								   }

								   @Override
								   public String getValue() {
									   return "Instituto ITN";
								   }
							   })));
	}


	private List<SecurityReference> authorization() {
		AuthorizationScope		scope	= new AuthorizationScope("REST", "Authorized REST Services");
		AuthorizationScope[]	scopes	= new AuthorizationScope[1];
		scopes[0] 						= scope;
		return Collections.singletonList(new SecurityReference("APIKEY", scopes));
	}
}
