package com.subtrack.subtrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SubTrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubTrackApplication.class, args);
	}

}
