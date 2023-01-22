package com.kronusboss.cine.adapter.util;

import java.util.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kronusboss.cine.adapter.controller.core.dto.UserTokenDto;

public class CredentialUtil {
	
	public static UserTokenDto getUserFromToken(String token) throws JsonMappingException, JsonProcessingException {
		String tokenSemBearer = token.substring(7);
		String tokenPayloadBase64 = tokenSemBearer.split("[.]")[1];
		String payload = new String(Base64.getDecoder().decode(tokenPayloadBase64));
		
		ObjectMapper mapper = new ObjectMapper();
		
		UserTokenDto user = mapper.readValue(payload, UserTokenDto.class);
		
		if(user.getLogin() == null || user.getAudiencia() == null || user.getExpiracao() == null || user.getName() == null) {
			throw new IllegalArgumentException();
		}
		
		return user;
	}

}
