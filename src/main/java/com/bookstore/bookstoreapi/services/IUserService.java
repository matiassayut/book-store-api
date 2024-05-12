package com.bookstore.bookstoreapi.services;

import com.bookstore.bookstoreapi.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    public User insertOrUpdate(User user);

    public Optional<User> getUserById(int id) throws Exception;

    public boolean deleteUserById(int id);

    public List<User> getAllUsers();

}
