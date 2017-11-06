package com.samsung.fas.pir;

import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJSONDoc
public class PirApplication {
	public static void main(String[] args) {
		SpringApplication.run(PirApplication.class, args);
	}
}