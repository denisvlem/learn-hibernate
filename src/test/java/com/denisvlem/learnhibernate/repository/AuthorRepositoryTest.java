package com.denisvlem.learnhibernate.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.denisvlem.learnhibernate.entity.Author;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AuthorRepositoryTest {

  @Autowired
  protected AuthorRepository authorRepository;

  @AfterEach
  public void cleanUp() {
    authorRepository.deleteAll();
  }

  @Test
  void givenRepositoryRequest_DontSwitchVersion() {
    //given
    var denisVer0 = authorRepository.save(
        new Author().setFirstName("Denis").setLastName("Emelyanov"));
    assertThat(denisVer0.getVersion()).isZero();

    //when
    var denisVer1 = authorRepository.save(denisVer0.setLastName("asdfdf"));

    //then
    assertThat(denisVer1.getVersion()).isZero();
  }
}
