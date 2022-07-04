package com.denisvlem.learnhibernate.service;


import com.denisvlem.learnhibernate.entity.Author;
import com.denisvlem.learnhibernate.repository.dao.AuthorDao;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class AuthorServiceJdbcImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public Author getById(long id) {
        return null;
    }

    @Override
    public Author create(String firstName, String lastName) {
        return null;
    }

    @Override
    public Author update(Author author) {
        return null;
    }
}
