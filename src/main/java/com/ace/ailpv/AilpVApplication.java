package com.ace.ailpv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SecretConfigProperties.class)
public class AilpVApplication {

	public static void main(String[] args) {
		SpringApplication.run(AilpVApplication.class, args);
	}

}