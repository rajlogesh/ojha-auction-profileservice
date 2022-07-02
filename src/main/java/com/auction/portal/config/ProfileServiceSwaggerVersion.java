package com.auction.portal.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@ComponentScan(basePackages = { "com.auction.portal" })
@ComponentScan(basePackages = { "com.stk.mrkt.ojhastkmrktprofileservice" })
public class ProfileServiceSwaggerVersion {

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("springshop-public").packagesToScan("com.auction.portal.controller")
				.pathsToMatch("/e-auction/api/v1/**").build();
	}

	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Ojhas Profile Services")
						.description("Profile service is used to manage users for online auction portal").version("v0.0.1")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")))
				.externalDocs(new ExternalDocumentation().description("SpringShop Wiki Documentation")
						.url("https://springshop.wiki.github.org/docs"));
	}
}
