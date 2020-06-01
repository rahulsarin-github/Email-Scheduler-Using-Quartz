package com.example.project.emailscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class QuartzDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuartzDemoApplication.class, args);
	}
}
