package com.taskgpb.intern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class VkApiServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(VkApiServiceApplication.class, args);
	}

}
