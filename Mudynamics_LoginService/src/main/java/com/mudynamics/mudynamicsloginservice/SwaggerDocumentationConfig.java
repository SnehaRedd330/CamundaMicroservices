package com.mudynamics.mudynamicsloginservice;

import static io.swagger.models.auth.In.HEADER;
import static java.util.Collections.singletonList;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class SwaggerDocumentationConfig {

	 ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	            .title("Mudynamics Login API Service")
	            .description(" Mudynamics Login Service Request API")
	            .license("TechMahindra")
	            .licenseUrl("http://www.techmahindra.com/licenses/LICENSE-1.0.html")
	            .termsOfServiceUrl("https://techmahindra.com")
	            .version("1.0.0")
	            .contact(new Contact("Shesharao Joshi","", "SJ00495527@techmahindra.com"))
	            .build();
	    }
	 
	 @Bean
	    public Docket customImplementation(){
	        return new Docket(DocumentationType.SWAGGER_2)
	        		 .securitySchemes(singletonList(new ApiKey("JWT", AUTHORIZATION, HEADER.name())))
	        	        .securityContexts(singletonList(
	        	            SecurityContext.builder()
	        	                .securityReferences(
	        	                    singletonList(SecurityReference.builder()
	        	                        .reference("JWT")
	        	                        .scopes(new AuthorizationScope[0])
	        	                        .build()
	        	                    )
	        	                )
	        	                .build())
	        	        )
	                .select()
	                    .apis(RequestHandlerSelectors.basePackage("com.mudynamics.mudynamicsloginservice"))
	                    .build()
	                 .apiInfo(apiInfo());
	    }

}
