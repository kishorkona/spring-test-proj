package com.test.config;

import com.test.resolvers.ApiVersionHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

@Configuration
public class WebConfig extends DelegatingWebMvcConfiguration {
	@Bean
    @Override
    public ApiVersionHandlerMapping requestMappingHandlerMapping(
    		ContentNegotiationManager contentNegotiationManager, 
    		FormattingConversionService conversionService,
    		ResourceUrlProvider resourceUrlProvider) {
		System.out.println("--WebConfig->DelegatingWebMvcConfiguration--");
    	return new ApiVersionHandlerMapping();
    }

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}