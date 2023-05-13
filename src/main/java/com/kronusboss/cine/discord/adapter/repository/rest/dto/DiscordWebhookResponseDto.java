package com.kronusboss.cine.discord.adapter.repository.rest.dto;

import com.kronusboss.cine.discord.domain.DiscordWebhookInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscordWebhookResponseDto {
	private String id;

	public DiscordWebhookInfo toDomain() {
		return DiscordWebhookInfo.builder().messageId(id).build();
	}
}
