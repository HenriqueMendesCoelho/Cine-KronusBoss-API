package com.kronusboss.cine.movie.adapter.repository.rest.dto;

import java.time.OffsetDateTime;

import com.kronusboss.cine.movie.domain.MovieNote;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieNoteRestRequestDto {

	private Integer note;
	private OffsetDateTime createdAt;
	private OffsetDateTime updatedAt;
	private MovieNoteUserRestRequestDto user;

	public MovieNoteRestRequestDto(MovieNote movieNote) {
		note = movieNote.getNote() != null ? movieNote.getNote() : null;
		createdAt = movieNote.getCreatedAt() != null ? movieNote.getCreatedAt() : null;
		updatedAt = movieNote.getUpdatedAt() != null ? movieNote.getUpdatedAt() : null;
		user = new MovieNoteUserRestRequestDto(movieNote.getUser());
	}

}
