package com.bookstore.bookstoreapi.services.implementation;

import com.bookstore.bookstoreapi.dtos.UserDTO;
import com.bookstore.bookstoreapi.entities.User;
import com.bookstore.bookstoreapi.repositories.IUserRepository;
import com.bookstore.bookstoreapi.services.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public User insertOrUpdate(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(int id) throws Exception {
        return userRepository.findById(id);
    }

    @Override
    public boolean deleteUserById(int id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserDTO> getAllUsersByBookId(int id) {
        return userRepository.findByBookId(id).stream()
                .map(User -> modelMapper.map(User, UserDTO.class))
                .collect(Collectors.toList());
    }

}
