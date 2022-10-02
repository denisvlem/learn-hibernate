package com.denisvlem.learnhibernate.service;

import com.denisvlem.learnhibernate.dto.AddBookRequestDto;
import com.denisvlem.learnhibernate.entity.Book;
import com.denisvlem.learnhibernate.repository.BookRepository;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Book service for business rules.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final AuthorService authorService;

  private final MockService mockService;

  /**
   * Add book to the db.
   *
   * @param requestDto - book dto
   * @return newly created book entity
   */
  @Transactional
  public Book addBook(AddBookRequestDto requestDto) {
    log.info("Start BookService#addBook");
    var author = authorService.getById(requestDto.getAuthorId());

    var book = new Book()
        .setTitle(requestDto.getTitle())
        .setGenre(requestDto.getGenre())
        .setAuthor(author);

    var savedBook = bookRepository.save(book);

    //In normal cases does nothing, placed here for imitation something in tests
    mockService.doSomething();
    log.info("End BookService#addBook");
    return savedBook;
  }

  @Transactional
  public void delete(@Valid @NotNull Long bookId) {
    bookRepository.deleteById(bookId);
  }
}
