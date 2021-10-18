package com.rachadel.config.docs;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author Manoel Rachadel Neto
 * @since  17 de out. de 2021
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket apiDoc() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
					.apis(RequestHandlerSelectors.any())
					.paths(regex("/v1.*"))
					.build()
				.apiInfo(metaData());
	}
	
	private ApiInfo metaData() {
		return new ApiInfoBuilder() 
				.title("Spring Project")
				.description("")
				.version("1.0)")
				.contact(new Contact("Manoel Rachadel Neto", 
									"https://github.com/manoel-rachadel",
									"manoelch13@gmail.com"))
				.license("Apache License Version 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0").build();
						
	}
}
