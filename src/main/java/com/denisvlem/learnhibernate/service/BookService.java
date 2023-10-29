package com.denisvlem.learnhibernate.service;

import com.denisvlem.learnhibernate.dto.AddBookRequestDto;
import com.denisvlem.learnhibernate.entity.Book;
import com.denisvlem.learnhibernate.repository.BookRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
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
  private final GenreService genreService;

  /**
   * Add book to the db.
   *
   * @param requestDto - book dto
   * @return newly created book entity
   */
  @Transactional
  public Book addBook(@Valid AddBookRequestDto requestDto) {

    var book = new Book().setTitle(requestDto.getTitle())
        .setDescription(requestDto.getDescription());

    book.addGenres(genreService.getGenres(requestDto.getGenreIds()));
    book.addAuthors(authorService.getAuthors(requestDto.getAuthorIds()));

    return bookRepository.save(book);
  }

  @Transactional
  public void delete(@Valid @NotNull UUID bookId) {
    bookRepository.deleteById(bookId);
  }

  @Transactional(readOnly = true)
  public List<Book> getBooks() {
    return bookRepository.findAll();
  }
}
