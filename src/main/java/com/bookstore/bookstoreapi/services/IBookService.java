package com.bookstore.bookstoreapi.services;

import com.bookstore.bookstoreapi.dtos.BookDTO;
import com.bookstore.bookstoreapi.entities.Book;

import java.util.List;
import java.util.Optional;

public interface IBookService {

    public Book insertOrUpdate(Book book);

    public Optional<Book> getBookById(int id) throws Exception;

    public boolean deleteBookById(int id);

    public List<Book> getAllBooks();

    public List<BookDTO> getAllBooksByAuthor(String author);

    public List<BookDTO> getAllBooksByGenreId(int id);

}
