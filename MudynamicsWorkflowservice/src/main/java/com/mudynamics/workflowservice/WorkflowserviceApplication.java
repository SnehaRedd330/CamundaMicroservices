package com.mudynamics.workflowservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = "*")
@SpringBootApplication
@EnableJpaAuditing
public class WorkflowserviceApplication {

	 @Bean
	    public ModelMapper modelMapper() {
	        return new ModelMapper();
	    }
	 @Bean
		public RestTemplate restTemplate() {
		    return new RestTemplate();
		}
	public static void main(String[] args) {
		SpringApplication.run(WorkflowserviceApplication.class, args);
	}
}
