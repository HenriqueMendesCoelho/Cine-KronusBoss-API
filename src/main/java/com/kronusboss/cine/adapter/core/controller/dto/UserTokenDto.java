package com.kronusboss.cine.adapter.core.controller.dto;

import java.util.Set;
import java.util.UUID;

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
	@JsonProperty("name")
	private String name;
	@JsonProperty("roles")
	private Set<String> roles;
	@JsonProperty("id")
	private UUID id;
}
