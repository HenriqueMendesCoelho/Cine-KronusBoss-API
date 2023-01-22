package com.kronusboss.cine.adapter.controller.core.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserLoginDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
}
