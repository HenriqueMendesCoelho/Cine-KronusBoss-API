package com.kronusboss.cine.user.adapter.controller.dto;

import com.kronusboss.cine.user.domain.Invite;

import lombok.Data;

@Data
public class InviteResponseDto {

	private String code;

	public InviteResponseDto(Invite invite) {
		code = invite.getCode();
	}

}
