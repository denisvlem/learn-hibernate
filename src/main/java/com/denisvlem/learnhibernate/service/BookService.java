package com.denisvlem.learnhibernate.service;

import com.denisvlem.learnhibernate.dto.AddBookRequestDto;
import com.denisvlem.learnhibernate.entity.Book;
import com.denisvlem.learnhibernate.repository.BookRepository;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Book service for business rules.
 */
@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final AuthorService authorService;

  /**
   * Add book to the db.
   *
   * @param requestDto - book dto
   * @return newly created book entity
   */
  @Transactional
  public Book addBook(AddBookRequestDto requestDto) {
    var author = authorService.getById(requestDto.getAuthorId());

    var book = new Book()
        .setTitle(requestDto.getTitle())
        .setGenre(requestDto.getGenre())
        .setAuthor(author);

    return bookRepository.save(book);
  }

  @Transactional
  public void delete(@Valid @NotNull Long bookId) {
    bookRepository.deleteById(bookId);
  }
}
