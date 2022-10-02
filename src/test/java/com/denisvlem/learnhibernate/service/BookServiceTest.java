package com.denisvlem.learnhibernate.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.denisvlem.learnhibernate.dto.AddBookRequestDto;
import com.denisvlem.learnhibernate.entity.Author;
import com.denisvlem.learnhibernate.entity.Book;
import com.denisvlem.learnhibernate.repository.AuthorRepository;
import com.denisvlem.learnhibernate.repository.BookRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(classes = {ServiceTestConfiguration.class})
@ActiveProfiles("test")
@Transactional(propagation = Propagation.NEVER)
@Slf4j
class BookServiceTest {

  @Autowired
  protected TransactionTemplate tx;
  @Autowired
  protected AuthorRepository authorRepository;
  @Autowired
  protected BookRepository bookRepository;
  @Autowired
  protected BookService bookService;

  @MockBean
  protected MockService mockService;

  @AfterEach
  public void cleanDb() {
    bookRepository.deleteAll();
    authorRepository.deleteAll();
  }


  @Test
  void givenAuthor_whenCreateBook_ShouldAddOneToDb() {

    //given
    var givenAuthor = createAuthor();
    assertThat(givenAuthor).isNotNull();

    var givenRequestBody = new AddBookRequestDto()
        .setAuthorId(givenAuthor.getAuthorId())
        .setGenre(1)
        .setTitle("Test Book Title");

    //when
    var expectedBook = bookService.addBook(givenRequestBody);

    //then
    List<Book> allBooks = bookRepository.findAll();
    assertThat(allBooks).asList().hasSize(1);

    tx.executeWithoutResult(s -> {

      var actualBook = allBooks.get(0);
      assertThat(actualBook.getBookId()).isEqualTo(expectedBook.getBookId());
      assertThat(actualBook.getTitle()).isEqualTo(expectedBook.getTitle());

      var author = actualBook.getAuthor();
      assertThat(author.getAuthorId()).isEqualTo(givenAuthor.getAuthorId());
    });
  }

  @Test
  void givenAuthorServiceException_whenCreateBook_ShouldRollback() {

    log.info("START TEST givenAuthorServiceException_whenCreateBook_ShouldAddOneToDb start");
    //given
    var givenAuthor = createAuthor();
    assertThat(givenAuthor).isNotNull();
    assertThat(authorRepository.findAll()).hasSize(1);
    assertThat(bookRepository.findAll()).isEmpty();

    final var givenRequestBody = new AddBookRequestDto()
        .setAuthorId(givenAuthor.getAuthorId())
        .setGenre(1)
        .setTitle("Test Book Title");

    Mockito.doThrow(new IllegalArgumentException()).when(mockService).doSomething();

    //when
    assertThatThrownBy(() -> bookService.addBook(givenRequestBody))
        .isInstanceOf(IllegalArgumentException.class);

    //then
    tx.executeWithoutResult(s -> {
      var allBooks = bookRepository.findAll();
      assertThat(allBooks).asList().isEmpty();
    });
    log.info("END TEST givenAuthorServiceException_whenCreateBook_ShouldAddOneToDb");
  }

  @Test
  void givenBookPersisted_whenDeleteRequested_shouldDelete() {
    //given
    var givenAuthor = createAuthor();
    assertThat(givenAuthor).isNotNull();
    assertThat(authorRepository.findAll()).asList().isNotEmpty();

    var givenRequestBody = new AddBookRequestDto()
        .setAuthorId(givenAuthor.getAuthorId())
        .setGenre(1)
        .setTitle("Test Book Title");

    var persistedBook = bookService.addBook(givenRequestBody);

    //when

    bookService.delete(persistedBook.getBookId());


    //then
    assertThat(bookRepository.findAll()).asList().isEmpty();
  }

  private Author createAuthor() {
    return tx.execute(s -> authorRepository.save(
            new Author()
                .setFirstName("Stephen")
                .setLastName("King")
        )
    );
  }
}