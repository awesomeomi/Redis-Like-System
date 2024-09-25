package com.example.redissystem;

import com.example.redissystem.service.InMemoryStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableScheduling
public class RedisSystemApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(RedisSystemApplication.class, args);

		// Load initial data
		InMemoryStoreService storeService = context.getBean(InMemoryStoreService.class);
		storeService.loadSnapshot(); // Load from snapshot file
		storeService.loadAOF();      // Load from AOF file
	}

}
