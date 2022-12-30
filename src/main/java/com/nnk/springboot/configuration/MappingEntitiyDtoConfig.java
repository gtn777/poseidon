package com.nnk.springboot.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappingEntitiyDtoConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
