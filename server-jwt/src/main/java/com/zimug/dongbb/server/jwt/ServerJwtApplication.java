package com.zimug.dongbb.server.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication(scanBasePackages={"com.zimug"})
public class ServerJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerJwtApplication.class, args);
	}

}
