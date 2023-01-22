package com.kronusboss.cine.adapter.controller.user.dto;

import com.kronusboss.cine.domain.user.Invite;

import lombok.Data;

@Data
public class InviteResponseDto {
	
	private String code;
	
	public InviteResponseDto(Invite invite) {
		code = invite.getCode();
	}
	
}
