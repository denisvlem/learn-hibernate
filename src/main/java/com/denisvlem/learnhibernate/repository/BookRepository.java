package com.denisvlem.learnhibernate.repository;

import com.denisvlem.learnhibernate.entity.Book;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Book jpa repository.
 */
public interface BookRepository extends JpaRepository<Book, UUID> {
}
