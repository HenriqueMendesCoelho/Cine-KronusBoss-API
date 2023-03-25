package com.kronusboss.cine.application.spring.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kronusboss.cine.application.spring.security.JWTAuthenticationFilter;
import com.kronusboss.cine.application.spring.security.JWTAuthorizationFilter;
import com.kronusboss.cine.application.spring.security.util.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;

	@Autowired
	private JWTUtil jwtUtil;

	private static final String[] PUBLIC_MATCHERS = { "/api/auth/forgot", };

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http.authorizeHttpRequests(authz -> authz.requestMatchers(PUBLIC_MATCHERS)
				.permitAll()
				.requestMatchers(HttpMethod.POST, "/api/user")
				.permitAll()
				.requestMatchers(HttpMethod.GET, "/actuator/**")
				.hasAuthority("ROLE_ADMIN")
				.anyRequest()
				.authenticated());
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(authenticationConfiguration), jwtUtil));
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(authenticationConfiguration), jwtUtil,
				userDetailsService));
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						// .allowedOrigins("https://*.kronusboss.com")
						.allowedOrigins("*")
						.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE");

			}
		};
	}
}
