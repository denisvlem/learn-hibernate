package com.denisvlem.learnhibernate.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.denisvlem.learnhibernate.BasicJpaTest;
import com.denisvlem.learnhibernate.dto.AddBookRequestDto;
import com.denisvlem.learnhibernate.entity.Author;
import com.denisvlem.learnhibernate.entity.Book;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;


@Slf4j
@ContextConfiguration(classes = {ServiceTestConfiguration.class})
class BookServiceTest extends BasicJpaTest {

  @Autowired
  protected BookService bookService;

  @Test
  void givenAuthor_whenCreateBook_ShouldAddOneToDb() {

    //given
    var givenAuthor = createAuthor();
    assertThat(givenAuthor).isNotNull();

    var givenRequestBody = new AddBookRequestDto()
        .setTitle("Test Book Title")
        .setAuthorIds(Set.of())
        .setGenreIds(Set.of());

    //when
    var expectedBook = bookService.addBook(givenRequestBody);

    //then
    List<Book> allBooks = bookRepository.findAll();
    assertThat(allBooks).asList().hasSize(1);

    tx.executeWithoutResult(s -> {

      var actualBook = allBooks.get(0);
      assertThat(actualBook.getBookId()).isEqualTo(expectedBook.getBookId());
      assertThat(actualBook.getTitle()).isEqualTo(expectedBook.getTitle());
      assertThat(genreRepository.findAll()).isEmpty();
    });
  }

  @Test
  void givenBookPersisted_whenDeleteRequested_shouldDelete() {
    //given
    var givenAuthor = createAuthor();
    assertThat(givenAuthor).isNotNull();
    assertThat(authorRepository.findAll()).asList().isNotEmpty();

    var givenRequestBody = new AddBookRequestDto()
        .setTitle("Test Book Title")
        .setGenreIds(Set.of())
        .setAuthorIds(Set.of());

    var persistedBook = bookService.addBook(givenRequestBody);

    //when
    bookService.delete(persistedBook.getBookId());

    //then
    assertThat(bookRepository.findAll()).asList().isEmpty();
    var all = genreRepository.findAll();
    assertThat(all).isEmpty();
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