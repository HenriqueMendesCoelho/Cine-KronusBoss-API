package com.kronusboss.cine.adapter.movie.controller.dto;

import java.util.UUID;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieNoteRequestDto {

	private UUID movieId;

	@Range(min = 0, max = 10)
	private Integer note;
}
