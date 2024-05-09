package com.bookstore.bookstoreapi.services;

import com.bookstore.bookstoreapi.entities.Book;
import com.bookstore.bookstoreapi.entities.Genre;

import java.util.List;
import java.util.Optional;

public interface IGenreService {

    public Genre insertOrUpdate(Genre genre);

    public Optional<Genre> getGenreById(int id) throws Exception;

    public Optional<Genre> getGenreByName(String name) throws Exception;

    public boolean deleteGenreById(int id);

    public List<Genre> getAllGenres();

}
