package com.bot.vk.vkbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VKBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(VKBotApplication.class, args);
    }
}