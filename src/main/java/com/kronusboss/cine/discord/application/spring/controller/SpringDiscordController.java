package com.kronusboss.cine.discord.application.spring.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kronusboss.cine.discord.adapter.controller.DiscordController;

@RestController
@RequestMapping("/api/discord")
public class SpringDiscordController {

	@Autowired
	private DiscordController controller;

	@PostMapping("/{movieId}")
	@PreAuthorize("hasRole('ADM')")
	public ResponseEntity<?> sendMovieMessageDiscord(@PathVariable UUID movieId) {
		controller.execute(movieId);

		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{movieId}")
	@PreAuthorize("hasRole('ADM')")
	public ResponseEntity<?> updateMovieMessageDiscord(@PathVariable UUID movieId) {
		controller.execute(movieId);

		return ResponseEntity.ok().build();
	}

}
