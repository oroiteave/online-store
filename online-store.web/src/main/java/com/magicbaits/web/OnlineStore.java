package com.magicbaits.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.magicbaits"})
public class OnlineStore {

	public static void main(String[] args) {
		SpringApplication.run(OnlineStore.class, args);
	}

}