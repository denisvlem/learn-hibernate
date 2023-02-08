package com.denisvlem.learnhibernate.service;

import com.denisvlem.learnhibernate.dto.AddBookRequestDto;
import com.denisvlem.learnhibernate.entity.Book;
import com.denisvlem.learnhibernate.repository.BookRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
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
  public void delete(@Valid @NotNull UUID bookId) {
    bookRepository.deleteById(bookId);
  }
}
