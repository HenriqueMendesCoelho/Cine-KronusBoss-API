package com.moviemux.movie.adapter.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moviemux.movie.domain.MovieNote;
import com.moviemux.movie.domain.MovieNoteKey;

public interface MovieNoteJpaRepository extends JpaRepository<MovieNote, MovieNoteKey> {

}
