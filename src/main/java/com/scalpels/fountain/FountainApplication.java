package com.scalpels.fountain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@Configuration
@MapperScan("com.scalpels.fountain.mapper")
public class FountainApplication {

	public static void main(String[] args) {
		SpringApplication.run(FountainApplication.class, args);
	}

}
