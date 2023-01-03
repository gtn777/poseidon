package com.nnk.springboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingFilterConfig {

	@Bean
	CommonsRequestLoggingFilter logFilter() {
		CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
		
		filter.setIncludeQueryString(false);
		filter.setIncludePayload(true);
		filter.setMaxPayloadLength(500);
		filter.setIncludeHeaders(false);
		filter.setAfterMessagePrefix("REQUEST DATA : ");
		filter.setBeforeMessagePrefix("BEFORE REQUEST:");
		
		return filter;
	}
}
