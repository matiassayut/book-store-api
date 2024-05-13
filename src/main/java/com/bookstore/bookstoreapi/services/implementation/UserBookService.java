package com.bookstore.bookstoreapi.services.implementation;

import com.bookstore.bookstoreapi.dtos.BookDTO;
import com.bookstore.bookstoreapi.dtos.UserDTO;
import com.bookstore.bookstoreapi.entities.UserBook;
import com.bookstore.bookstoreapi.repositories.IUserBookRepository;
import com.bookstore.bookstoreapi.services.IUserBookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserBookService implements IUserBookService {

    @Autowired
    private IUserBookRepository userBookRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserBook insertOrUpdate(UserBook userBook) {
        return userBookRepository.save(userBook);
    }

    @Override
    public Optional<UserBook> getUserBookById(int id) throws Exception {
        return userBookRepository.findById(id);
    }

    @Override
    public Optional<UserBook> getUserBookByIdCombined(int userId, int bookId) throws Exception {
        return userBookRepository.findByUserIdAndBookId(userId,bookId);
    }

    @Override
    public boolean deleteUserBookById(int id) {
        try {
            userBookRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean deleteUserBookByIdCombined(int userId, int bookId) {
        try {
            Optional<UserBook> userBook = userBookRepository.findByUserIdAndBookId(userId,bookId);
            userBook.ifPresent(userBookOptional -> userBookRepository.deleteById(userBookOptional.getId()));
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<UserBook> getAllUserBooks() {
        return userBookRepository.findAll();
    }

}
