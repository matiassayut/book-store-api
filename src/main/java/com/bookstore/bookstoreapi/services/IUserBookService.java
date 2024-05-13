package com.bookstore.bookstoreapi.services;

import com.bookstore.bookstoreapi.entities.UserBook;

import java.util.List;
import java.util.Optional;

public interface IUserBookService {

    public UserBook insertOrUpdate(UserBook userBook);

    public Optional<UserBook> getUserBookById(int id) throws Exception;

    public Optional<UserBook> getUserBookByIdCombined(int userId, int bookId) throws Exception;

    public boolean deleteUserBookById(int id);

    public boolean deleteUserBookByIdCombined(int userId, int bookId);

    public List<UserBook> getAllUserBooks();

}
