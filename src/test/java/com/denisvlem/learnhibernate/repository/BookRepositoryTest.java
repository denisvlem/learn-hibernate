package com.denisvlem.learnhibernate.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.denisvlem.learnhibernate.BasicJpaTest;
import com.denisvlem.learnhibernate.entity.Author;
import com.denisvlem.learnhibernate.entity.Book;
import com.denisvlem.learnhibernate.entity.Genre;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BookRepositoryTest extends BasicJpaTest {

  @Test
  void givenBook_whenSave_thenCreateAuthorsAndGenres() {
    tx.executeWithoutResult(s -> {
      var author = new Author()
          .setFirstName("firstName")
          .setMiddleName("middleName")
          .setLastName("lastName")
          .setDescription("description");

      var savedAuthor = authorRepository.save(author);

      var genre = new Genre()
          .setName("name");

      var savedGenre = genreRepository.save(genre);

      var book = new Book()
          .setAuthors(Set.of(savedAuthor))
          .setDescription("bookDescription")
          .setTitle("testTitle")
          .setGenres(Set.of(savedGenre));

      bookRepository.save(book);
    });

    assertThat(authorRepository.findAll()).isNotEmpty();
    assertThat(genreRepository.findAll()).isNotEmpty();
  }
}