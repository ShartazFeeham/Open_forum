package com.microforum.posts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Micro-forum Posts microservice REST API",
				description = "Micro-forum Posts microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Shartaz Feeham",
						email = "shartaz.yeasar@gmail.com",
						url = "https://github.com/ShartazFeeham/Micro_forum"
				)
		),
		externalDocs = @ExternalDocumentation(
				description =  "Micro-forum Posts microservice REST API Documentation",
				url = "www.postman.com/__coming__soon"
		)
)
public class PostsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostsApplication.class, args);
	}

}
