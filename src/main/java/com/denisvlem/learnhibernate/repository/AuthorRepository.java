package com.denisvlem.learnhibernate.repository;

import com.denisvlem.learnhibernate.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
