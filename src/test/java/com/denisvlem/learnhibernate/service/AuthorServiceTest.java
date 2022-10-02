package com.denisvlem.learnhibernate.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.denisvlem.learnhibernate.BasicMsTest;
import com.denisvlem.learnhibernate.entity.Author;
import com.denisvlem.learnhibernate.entity.Book;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@Slf4j
class AuthorServiceTest extends BasicMsTest {

  @Test
  void testOptimisticLock_SwitchVersion() {
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
    assertThat(denisVer1.getVersion()).isZero();
  }

  @Test
  void givenRepeatableRequest_whenDone_shouldThrowOptimisticLockException() {
    //given
    var denisVer0 = authorService.create("Denis", "Emelyanov");
    assertThat(denisVer0.getVersion()).isZero();

    log.info("Version 0 is created");

    //when
    authorService.update(denisVer0.setFirstName("Andrew"));
    log.info("Version 0 is updated to 1");

    //then
    assertThatThrownBy(
        () -> tx.executeWithoutResult(
            s -> authorService.update(denisVer0.setFirstName("Andrew")
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

  @Test
  void givenOneAuthorWithBooksPersisted_whenDeleteRequested_shouldDeleteBooksAlsoFromDb() {
    //given
    var persistedAuthorId = saveAuthor();

    assertThat(bookRepository.findAll()).asList().isEmpty();

    var persistedAuthor = authorRepository.getReferenceById(persistedAuthorId);
    bookRepository.saveAll(
        List.of(
            new Book().setGenre(1).setAuthor(persistedAuthor).setTitle("testBook1"),
            new Book().setGenre(1).setAuthor(persistedAuthor).setTitle("testBook2")
        )
    );
    assertThat(bookRepository.findAll()).asList().hasSize(2);

    //when
    authorService.delete(persistedAuthorId);

    //then
    assertThat(authorRepository.findAll()).asList().isEmpty();
    assertThat(bookRepository.findAll()).asList().isEmpty();
  }

  @Test
  void givenOneAuthorWithBooks_whenAuthorSave_shouldAlsoSaveBooks() {
    //given
    assertThat(authorRepository.findAll()).asList().isEmpty();
    assertThat(bookRepository.findAll()).asList().isEmpty();

    var persistedAuthor = new Author().setFirstName("Denis").setLastName("Emelyanov");

    persistedAuthor.setBooks(Set.of(
        new Book().setGenre(1).setAuthor(persistedAuthor).setTitle("testBook1"),
        new Book().setGenre(1).setAuthor(persistedAuthor).setTitle("testBook2")
    ));

    //when
    var updatedAuthor = authorService.createNew(persistedAuthor);

    //then
    tx.executeWithoutResult(s -> {
      var allAuthors = authorRepository.findAll();
      assertThat(allAuthors).asList().hasSize(1);
      assertThat(allAuthors.get(0)).isEqualTo(updatedAuthor);
      assertThat(bookRepository.findAll()).asList().hasSize(2);
    });
  }

  @Test
  void givenOneAuthorWithBooks_whenAuthorUpdatedWithAnotherSetOfBooks_shouldAddBooks() {

    //given
    var persistedAuthorId = saveAuthor();

    assertThat(bookRepository.findAll()).asList().isEmpty();

    var persistedAuthor = authorRepository.getReferenceById(persistedAuthorId);
    bookRepository.saveAll(
        List.of(
            new Book().setGenre(1).setAuthor(persistedAuthor).setTitle("testBook1"),
            new Book().setGenre(1).setAuthor(persistedAuthor).setTitle("testBook2")
        )
    );
    assertThat(bookRepository.findAll()).asList().hasSize(2);

    var authorWithBooks = tx.execute(s -> {
      var author = authorRepository.getReferenceById(persistedAuthorId);
      assertThat(author.getBooks()).hasSize(2);
      return author;
    });
    assertThat(authorWithBooks).isNotNull();

    authorWithBooks.setBooks(Set.of(
        new Book().setGenre(1).setAuthor(authorWithBooks).setTitle("testBook3"))
    );
    //when
    authorService.update(authorWithBooks);

    //then
    assertThat(authorRepository.findAll()).asList().hasSize(1);
    assertThat(bookRepository.findAll()).asList().hasSize(3);
  }

  @Test
  void givenCorruptedAuthor_whenUpdate_shouldThrowValidationException() {

    final var author = new Author();
    author.setFirstName("");

    assertThatThrownBy(() -> authorService.update(author))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void givenNullAuthorId_whenDeleteById_shouldThrowValidationException() {

    assertThatThrownBy(() -> authorService.delete(null))
        .isInstanceOf(ConstraintViolationException.class);
  }

  private Long saveAuthor() {
    var persistedAuthorId = tx.execute(s ->
        authorRepository.save(
            new Author().setFirstName("Denis").setLastName("Emelyanov")).getAuthorId()
    );
    assertThat(persistedAuthorId).isNotNull();
    return persistedAuthorId;
  }
}
