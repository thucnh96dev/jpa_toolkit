package com.thucnh96.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JpaToolApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(JpaToolApplication.class, args);
	}
}
