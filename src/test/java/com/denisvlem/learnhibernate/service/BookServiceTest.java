package com.denisvlem.learnhibernate.service;

import com.denisvlem.learnhibernate.dto.AddBookRequestDto;
import com.denisvlem.learnhibernate.entity.Author;
import com.denisvlem.learnhibernate.entity.Book;
import com.denisvlem.learnhibernate.repository.AuthorRepository;
import com.denisvlem.learnhibernate.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = {ServiceTestConfiguration.class})
@ActiveProfiles("test")
class BookServiceTest {

    @Autowired
    protected TransactionTemplate tx;
    @Autowired
    protected AuthorRepository authorRepository;
    @Autowired
    protected BookRepository bookRepository;
    @Autowired
    protected BookService bookService;

    @AfterEach
    public void cleanDB() {
        bookRepository.deleteAllInBatch();
        authorRepository.deleteAllInBatch();
    }


    @Test
    void givenAuthor_whenCreateBook_ShouldAddOneToDb() {

        //given
        var givenAuthor = tx.execute(s -> authorRepository.save(
                        new Author()
                                .setFirstName("Stephen")
                                .setLastName("King")
                )
        );
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
}