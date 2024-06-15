package com.kronusboss.cine.discord.application.spring.controller;

import com.kronusboss.cine.discord.adapter.controller.DiscordController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/discord")
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
		controller.update(movieId);

		return ResponseEntity.ok().build();
	}

}
