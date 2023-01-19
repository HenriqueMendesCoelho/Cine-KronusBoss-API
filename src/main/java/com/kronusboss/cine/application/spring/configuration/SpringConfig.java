package com.kronusboss.cine.application.spring.configuration;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class SpringConfig {

	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		JavaTimeModule module = new JavaTimeModule();
		return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.setSerializationInclusion(Include.NON_NULL)
				.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
				.registerModule(module)
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}
	
	 @Bean
	 @Primary
	  public LocaleResolver localeResolver() {
	      SessionLocaleResolver slr = new SessionLocaleResolver();
	      slr.setDefaultLocale(Locale.US);
	      slr.setLocaleAttributeName("session.current.locale");
	      slr.setTimeZoneAttributeName("session.current.timezone");
	      return slr;
	  }
}
