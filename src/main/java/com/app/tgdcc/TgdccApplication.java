package com.app.tgdcc;

import com.app.tgdcc.telegram.updatehandlers.GroupHandlers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class TgdccApplication {

	public static void main(String[] args) {
		SpringApplication.run(TgdccApplication.class, args);
	}
}
