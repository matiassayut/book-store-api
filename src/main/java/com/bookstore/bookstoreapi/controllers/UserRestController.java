package com.bookstore.bookstoreapi.controllers;

import com.bookstore.bookstoreapi.dtos.UserDTO;
import com.bookstore.bookstoreapi.entities.User;
import com.bookstore.bookstoreapi.services.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserRestController {

    @Autowired
    private IUserService userService;

    private ModelMapper modelMapper = new ModelMapper();

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> postUser(@RequestBody UserDTO userDTO){
        return new ResponseEntity<>(
                modelMapper.map(userService.insertOrUpdate(modelMapper.map(userDTO, User.class)), UserDTO.class), HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> putUser(@RequestBody UserDTO userDTO) throws Exception {
        Optional<User> user = userService.getUserById(userDTO.getId());
        if (user.isPresent()){
            if (userDTO.getUsername() != null) {
                user.get().setUsername(userDTO.getUsername());
            }
            if (userDTO.getEmail() != null) {
                user.get().setEmail(userDTO.getEmail());
            }
            return new ResponseEntity<>(
                    modelMapper.map(userService.insertOrUpdate(user.get()),UserDTO.class)
                    , HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id") int id) throws Exception {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(
                    modelMapper.map(user.get(), UserDTO.class)
                    , HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteUserById(@PathVariable(name = "id") int id) throws Exception {
        return new ResponseEntity<>(
                "Deleted User with ID: " + id + " - " + userService.deleteUserById(id)
                , HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getUsers(){
        return new ResponseEntity<>(
                userService.getAllUsers().stream()
                        .map(User -> modelMapper.map(User, UserDTO.class))
                        .collect(Collectors.toList())
                ,HttpStatus.OK);
    }

}
