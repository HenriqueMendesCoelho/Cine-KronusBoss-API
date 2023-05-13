package com.kronusboss.cine.user.adapter.controller.dto;

import com.kronusboss.cine.user.domain.Preferences;

import lombok.Data;

@Data
public class PreferencesResponseDto {

	private boolean notify;

	public PreferencesResponseDto(Preferences preferences) {
		notify = preferences.isNotify();
	}
}
