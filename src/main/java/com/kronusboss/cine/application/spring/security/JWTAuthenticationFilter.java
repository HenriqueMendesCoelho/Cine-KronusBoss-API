package com.kronusboss.cine.application.spring.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kronusboss.cine.adapter.core.controller.dto.UserLoginDto;
import com.kronusboss.cine.application.spring.security.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	private JWTUtil jwtUtil;

	private static Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			UserLoginDto creds = new ObjectMapper().readValue(request.getInputStream(), UserLoginDto.class);

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(),
					creds.getPassword(), new ArrayList<>());

			Authentication auth = authenticationManager.authenticate(authToken);

			logRequest(request, response);

			return auth;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
	
	@Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
		
		String username = ((UserSS) auth.getPrincipal()).getUsername();
		String name = ((UserSS) auth.getPrincipal()).getName();
		Set<String> roles = ((UserSS) auth.getPrincipal()).getRoles();
        String token = jwtUtil.generateToken(username, name, roles);
        
        long expires = new Date().getTime() + jwtUtil.expiration;
        
        response.setContentType("application/json");
        
        response.getWriter().append("{\"access_token\": \"" + token + "\", " +
        		"\"expires\": \"" + new Date(expires) + "\"} ");
	}

	private void logRequest(HttpServletRequest request, HttpServletResponse response) {
		log.info("[" + response.getStatus() + "]" + "[" + request.getMethod() + "]" + request.getRequestURI() + "[from:"
				+ request.getHeader("x-forwarded-for") + "]");
	}

	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {

			response.setStatus(401);
			response.setContentType("application/json");

			response.getWriter().append(jsonNormal());

			logRequest(request, response);
		}

		private String jsonNormal() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Not authorized\", "
                + "\"message\": \"Email or password are incorrect.\", "
                + "\"path\": \"/login\"}";
        }
	}
}
