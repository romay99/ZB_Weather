package com.example.zb_weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ZbWeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZbWeatherApplication.class, args);
	}

}
