package com.denisvlem.learnhibernate.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.denisvlem.learnhibernate.BasicMsTest;
import com.denisvlem.learnhibernate.dto.AddBookRequestDto;
import com.denisvlem.learnhibernate.entity.Author;
import com.denisvlem.learnhibernate.entity.Genre;
import io.restassured.RestAssured;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class BookControllerTest extends BasicMsTest {

  @Test
  void givenNoBooks_whenRequestCreate_shouldAddOne() {

    //given
    var authorId = tx.execute(s -> authorRepository
        .save(new Author().setFirstName("Irvin").setLastName("Yalom"))
        .getAuthorId());

    var genreId = tx.execute(s -> genreRepository.save(new Genre().setName("Psychotherapy novel"))
        .getId());
    assertThat(bookRepository.findAll()).asList().isEmpty();

    assertThat(authorId).isNotNull();
    assertThat(genreId).isNotNull();

    //when
    RestAssured.given()
        .body(new AddBookRequestDto("When Nietzsche Wept", "testDescription", Set.of(authorId), Set.of(genreId)))
        .when().post("/api/v1/book-service/book").then().statusCode(200);

    //then
    tx.executeWithoutResult(s -> {
      assertThat(bookRepository.findAll()).asList().hasSize(1);
      final var savedBook = bookRepository.findAll().get(0);
      var genres = savedBook.getGenres();
      assertThat(savedBook.getAuthors()).hasSize(1);
      assertThat(genres).hasSize(1);
    });

    assertThat(genreRepository.findAll()).hasSize(1);
    assertThat(authorRepository.findAll()).hasSize(1);
  }
}
