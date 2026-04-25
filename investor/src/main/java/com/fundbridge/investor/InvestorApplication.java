package com.fundbridge.investor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class InvestorApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvestorApplication.class, args);
	}

}
