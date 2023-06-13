package com.denisvlem.learnhibernate.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.denisvlem.learnhibernate.BasicMsTest;
import com.denisvlem.learnhibernate.entity.Author;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.TransactionSystemException;

@Slf4j
class AuthorServiceTest extends BasicMsTest {

  /**
   * Used to check the optimization lock versioning work.
   */
  @Test
  void givenPersistedEntity_whenUpdate_thenSwitchVersion() {
    //given
    var denisVer0 = authorService.create("Denis", "Emelyanov");
    assertThat(denisVer0.getVersion()).isZero();

    //when
    var denisVer1 = authorService.update(denisVer0.setFirstName("Andrey"));

    //then
    assertThat(denisVer1.getVersion()).isOne();
  }

  @Test
  void givenRepositoryRequest_whenNothingChanged_DontSwitchVersion() {
    //given
    var denisVer0 = authorRepository.save(
        new Author().setFirstName("Denis").setLastName("Emelyanov"));
    assertThat(denisVer0.getVersion()).isZero();

    //when
    var denisVer1 = authorRepository.save(denisVer0);

    //then
    //TODO: find out why one
    assertThat(denisVer1.getVersion()).isOne();
  }

  @Test
  void givenRepeatableRequest_whenDone_shouldThrowOptimisticLockException() {
    //given
    var authorVersionZero = authorService.create("Denis", "Emelyanov");
    assertThat(authorVersionZero.getVersion()).isZero();
    log.info("Version 0 is created");

    //when
    authorService.update(authorVersionZero.setFirstName("Andrew"));
    log.info("Version 0 is updated to 1");

    //then
    assertThatThrownBy(
        () -> tx.executeWithoutResult(
            s -> authorService.update(authorVersionZero.setFirstName("Andrew")
            ))
    ).isInstanceOf(ObjectOptimisticLockingFailureException.class);
  }

  @Test
  void givenOneAuthorPersisted_whenDeleteRequested_shouldDeleteFromDb() {
    //given
    var persistedAuthorId = tx.execute(s ->
        authorRepository.save(
            new Author().setFirstName("Denis").setLastName("Emelyanov")).getAuthorId()
    );
    assertThat(persistedAuthorId).isNotNull();

    //when
    authorService.delete(persistedAuthorId);

    //then
    assertThat(authorRepository.findAll()).asList().isEmpty();
  }

  //todo created to test validation violation, but the ConstraintViolationException is now found
  // wrapped inside the TransactionSystemException, find out why and how to know that actually
  // CVE was there
  @Test
  void givenCorruptedAuthor_whenUpdate_shouldThrowValidationException() {

    final var author = new Author();
    author.setFirstName("");

    assertThatThrownBy(() -> authorService.update(author))
        .isInstanceOf(TransactionSystemException.class);
  }

  @Test
  void givenNullAuthorId_whenDeleteById_shouldThrowValidationException() {

    assertThatThrownBy(() -> authorService.delete(null))
        .isInstanceOf(ConstraintViolationException.class);
  }
}
