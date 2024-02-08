package com.kp2s.sakapfet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;

@SpringBootApplication
@EnableReactiveElasticsearchRepositories
public class SakapfetApplication {


	public static void main(String[] args) {
		SpringApplication.run(SakapfetApplication.class, args);
	}

}
