package com.denisvlem.learnhibernate.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.denisvlem.learnhibernate.BasicJpaTest;
import com.denisvlem.learnhibernate.entity.Author;
import org.junit.jupiter.api.Test;

class AuthorRepositoryTest extends BasicJpaTest {

  @Test
  void givenRepositoryRequest_whenUpdated_shouldSwitchVersion() {
    //given
    var denisVer0 = authorRepository.save(
        new Author().setFirstName("Denis").setLastName("Emelyanov"));
    assertThat(denisVer0.getVersion()).isZero();

    //when
    var denisVer1 = authorRepository.save(denisVer0.setLastName("asdfdf"));

    //then
    assertThat(denisVer1.getVersion()).isOne();
  }
}
