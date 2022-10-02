package com.denisvlem.learnhibernate.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.denisvlem.learnhibernate.BasicMsTest;
import com.denisvlem.learnhibernate.dto.AddBookRequestDto;
import com.denisvlem.learnhibernate.entity.Author;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

class BookControllerTest extends BasicMsTest {

  @Test
  void givenNoBooks_whenRequestCreate_shouldAddOne() {

    //given
    var authorId = tx.execute(s -> authorRepository
        .save(new Author().setFirstName("Denis").setLastName("Emelyanov"))
        .getAuthorId());

    assertThat(bookRepository.findAll()).asList().isEmpty();

    //when
    RestAssured.given()
        .body(new AddBookRequestDto("author", 1, authorId))
        .when().post("/book").then().statusCode(200);

    //then
    assertThat(bookRepository.findAll()).asList().hasSize(1);
  }
}
