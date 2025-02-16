package com.kolown.porring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PorringApplication {

	public static void main(String[] args) {
		SpringApplication.run(PorringApplication.class, args);
	}

}
