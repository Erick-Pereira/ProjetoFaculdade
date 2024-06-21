package com.senac.CondoConnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CondoConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CondoConnectApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("toptop"));
	}

}
