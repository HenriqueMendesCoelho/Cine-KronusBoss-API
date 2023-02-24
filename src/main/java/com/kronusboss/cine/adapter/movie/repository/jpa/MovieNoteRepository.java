package com.kronusboss.cine.adapter.movie.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kronusboss.cine.movie.domain.MovieNote;
import com.kronusboss.cine.movie.domain.MovieNoteKey;

public interface MovieNoteRepository extends JpaRepository<MovieNote, MovieNoteKey> {

}
