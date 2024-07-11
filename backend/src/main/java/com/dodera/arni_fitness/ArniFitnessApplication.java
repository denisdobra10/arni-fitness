package com.dodera.arni_fitness;

import com.dodera.arni_fitness.security.TokenService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableConfigurationProperties(TokenService.Properties.class)
@EnableScheduling
public class ArniFitnessApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArniFitnessApplication.class, args);
	}
}
