package com.denisvlem.learnhibernate;

import com.denisvlem.learnhibernate.repository.AuthorRepository;
import com.denisvlem.learnhibernate.service.AuthorServiceJpaImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public abstract class BasicMsTest {

    @Autowired
    protected AuthorServiceJpaImpl authorService;

    @Autowired
    protected AuthorRepository authorRepository;

    @Autowired
    protected TransactionTemplate tx;

    @AfterEach
    public void cleanUp() {
        tx.executeWithoutResult(s -> authorRepository.deleteAll());
    }
}
