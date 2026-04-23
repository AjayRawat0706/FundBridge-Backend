package com.fundbridge.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class StartupApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartupApplication.class, args);
	}

}
