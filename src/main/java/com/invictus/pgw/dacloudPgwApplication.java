package com.dacloud.pgw;

import com.dacloud.pgw.global.configurations.KispayProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(KispayProperties.class)
public class dacloudPgwApplication {

	public static void main(String[] args) {
		SpringApplication.run(dacloudPgwApplication.class, args);
	}

}
