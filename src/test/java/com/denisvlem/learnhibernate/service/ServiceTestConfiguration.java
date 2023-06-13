package com.denisvlem.learnhibernate.service;

import com.denisvlem.learnhibernate.repository.AuthorRepository;
import com.denisvlem.learnhibernate.repository.BookRepository;
import com.denisvlem.learnhibernate.repository.GenreRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Test configuration for data jpa tests.
 */
@TestConfiguration
public class ServiceTestConfiguration {

  @Bean
  public BookService bookService(
      BookRepository bookRepository,
      AuthorServiceJpaImpl authorService,
      GenreService genreService
  ) {
    return new BookService(bookRepository, authorService, genreService);
  }

  @Bean
  public AuthorServiceJpaImpl authorService(AuthorRepository authorRepository) {
    return new AuthorServiceJpaImpl(authorRepository);
  }

  @Bean
  public GenreService genreService(GenreRepository genreRepository) {
    return new GenreService(genreRepository);
  }
}
