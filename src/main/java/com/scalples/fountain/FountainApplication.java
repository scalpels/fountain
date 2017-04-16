package com.scalples.fountain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@MapperScan("com.scalples.fountain.mapper")
public class FountainApplication {

	public static void main(String[] args) {
		SpringApplication.run(FountainApplication.class, args);
	}

}
