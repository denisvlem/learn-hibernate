package com.denisvlem.learnhibernate.service;

import com.denisvlem.learnhibernate.repository.AuthorRepository;
import com.denisvlem.learnhibernate.repository.BookRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ServiceTestConfiguration {

    @Bean
    public BookService bookService(BookRepository bookRepository, AuthorServiceJpaImpl authorService) {
        return new BookService(bookRepository, authorService);
    }

    @Bean
    public AuthorServiceJpaImpl authorService(AuthorRepository authorRepository) {
        return new AuthorServiceJpaImpl(authorRepository);
    }
}
