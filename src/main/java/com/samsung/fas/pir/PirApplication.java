package com.samsung.fas.pir;

import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableJSONDoc
@SpringBootApplication
public class PirApplication {
	public static void main(String[] args) {
		SpringApplication.run(PirApplication.class, args);
	}
}