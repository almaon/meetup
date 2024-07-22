package com.example.meetup.servicediscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MeetupServicediscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetupServicediscoveryApplication.class, args);
	}

}
