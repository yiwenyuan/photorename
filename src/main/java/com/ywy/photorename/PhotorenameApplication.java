package com.ywy.photorename;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.ywy")
@SpringBootApplication
public class PhotorenameApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotorenameApplication.class, args);
	}

}
