package com.denisvlem.learnhibernate.service;

import com.denisvlem.learnhibernate.entity.Author;

public interface AuthorService {

    Author getById(long id);

    Author create(String firstName, String lastName);

    Author update(Author author);
}
