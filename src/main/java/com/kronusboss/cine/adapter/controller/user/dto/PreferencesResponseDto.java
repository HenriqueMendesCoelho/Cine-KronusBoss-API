package com.kronusboss.cine.adapter.controller.user.dto;

import com.kronusboss.cine.domain.user.Preferences;

import lombok.Data;

@Data
public class PreferencesResponseDto {
	
	private boolean notify;
	
	public PreferencesResponseDto(Preferences preferences) {
		notify = preferences.isNotify();
	}
}
