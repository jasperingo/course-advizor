package com.lovelyn.course_advizor;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CourseAdvizorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseAdvizorApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		final ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

}
