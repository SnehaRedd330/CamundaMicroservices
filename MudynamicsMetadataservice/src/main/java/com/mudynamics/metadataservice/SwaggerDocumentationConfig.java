package com.mudynamics.metadataservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerDocumentationConfig {

	 ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	            .title("Mudynamics API")
	            .description(" Mudynamics Metadata Service Request API")
	            .license("TechM")
	            .licenseUrl("http://www.techmahindra.com/licenses/LICENSE-1.0.html")
	            .termsOfServiceUrl("https://techmahindra.com")
	            .version("1.0.0")
	            .contact(new Contact("Sneha P","", "SP00557779@techmahindra.com"))
	            .build();
	    }
	 
	 @Bean
	    public Docket customImplementation(){
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                    .apis(RequestHandlerSelectors.basePackage("com.mudynamics.metadataservice"))
	                    .build()
	                 .apiInfo(apiInfo());
	    }

}
