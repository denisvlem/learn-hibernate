package com.denisvlem.learnhibernate.service;

import com.denisvlem.learnhibernate.entity.Genre;
import com.denisvlem.learnhibernate.repository.GenreRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Genre service.
 */
@Service
@RequiredArgsConstructor
public class GenreService {

  private final GenreRepository genreRepository;

  @Transactional(readOnly = true)
  public Genre getGenre(UUID genreId) {

    return genreRepository.getReferenceById(genreId);
  }
}
