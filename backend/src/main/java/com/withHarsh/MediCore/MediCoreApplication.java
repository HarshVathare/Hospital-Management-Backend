package com.withHarsh.MediCore;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class MediCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediCoreApplication.class, args);
	}

}

