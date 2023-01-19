package com.kronusboss.cine.adapter.controller.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserTokenDto {
	
	@JsonProperty("sub")
	private String login;
	@JsonProperty("exp")
	private Long expiracao;
	@JsonProperty("aud")
	private String audiencia;
	@JsonProperty("email")
	private String email;
}
