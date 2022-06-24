package com.easyorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.easyorder.mapper")
public class EasyorderApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyorderApplication.class, args);
	}

}
