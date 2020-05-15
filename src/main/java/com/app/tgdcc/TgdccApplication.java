package com.app.tgdcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TgdccApplication {

	public static void main(String[] args) {
		SpringApplication.run(TgdccApplication.class, args);
		Controller controller = new Controller();
	}


}
