package com.kronusboss.cine.movie.usecase;

import java.util.UUID;

public interface DeleteMovieNoteUseCase {

	void delete(UUID userId, UUID movieId);

}
