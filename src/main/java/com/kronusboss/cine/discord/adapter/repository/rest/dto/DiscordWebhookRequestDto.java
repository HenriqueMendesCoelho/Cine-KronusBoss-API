package com.kronusboss.cine.discord.adapter.repository.rest.dto;

import java.util.List;

import com.kronusboss.cine.movie.domain.Movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DiscordWebhookRequestDto {

	public String content;
	public List<EmbedRequestDto> embeds;

	public DiscordWebhookRequestDto(Movie movie) {
		content = "@Cinéfilos";
		embeds = List.of(new EmbedRequestDto(movie));
	}

}
