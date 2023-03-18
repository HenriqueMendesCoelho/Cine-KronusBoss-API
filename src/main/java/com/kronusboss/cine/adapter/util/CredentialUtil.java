package com.kronusboss.cine.adapter.util;

import java.util.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kronusboss.cine.adapter.core.controller.dto.UserTokenDto;
import com.kronusboss.cine.adapter.util.exception.TokenInvalidException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CredentialUtil {

	public static UserTokenDto getUserFromToken(String token) throws TokenInvalidException {
		String tokenSemBearer = token.substring(7);
		String tokenPayloadBase64 = tokenSemBearer.split("[.]")[1];
		String payload = new String(Base64.getDecoder().decode(tokenPayloadBase64));

		ObjectMapper mapper = new ObjectMapper();

		try {
			UserTokenDto user = mapper.readValue(payload, UserTokenDto.class);

			if (user.getLogin() == null || user.getAudiencia() == null || user.getExpiracao() == null
					|| user.getName() == null || user.getId() == null) {
				log.error("Token not have all needed fields");
				throw new TokenInvalidException();
			}

			return user;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new TokenInvalidException();
		}

	}

}
