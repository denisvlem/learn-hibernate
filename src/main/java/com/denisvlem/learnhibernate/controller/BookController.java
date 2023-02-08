package com.denisvlem.learnhibernate.controller;

import com.denisvlem.learnhibernate.dto.AddBookRequestDto;
import com.denisvlem.learnhibernate.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Book rest controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
@Slf4j
public class BookController {

  private final BookService service;

  /**
   * Add a new book.
   *
   * @param requestDto - new book information
   */
  @PostMapping
  public void doPostAddBook(@RequestBody AddBookRequestDto requestDto) {
    log.info("Received [POST] /book request");
    service.addBook(requestDto);
    log.info("Successful[POST] /book request");
  }
}
