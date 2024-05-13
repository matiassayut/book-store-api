package com.bookstore.bookstoreapi.controllers;

import com.bookstore.bookstoreapi.dtos.BookDTO;
import com.bookstore.bookstoreapi.dtos.ResponseUserBookDTO;
import com.bookstore.bookstoreapi.dtos.UserBookDTO;
import com.bookstore.bookstoreapi.dtos.UserDTO;
import com.bookstore.bookstoreapi.entities.Genre;
import com.bookstore.bookstoreapi.entities.UserBook;
import com.bookstore.bookstoreapi.services.IUserBookService;
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
@RequestMapping(path = "api/v1/userbook")
public class UserBookRestController {

    @Autowired
    private IUserBookService userBookService;

    private ModelMapper modelMapper = new ModelMapper();

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserBookDTO> postUserBook(@RequestBody UserBookDTO userBookDTO) throws Exception {
        if (userBookDTO.getId() != 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (userBookDTO.getUserId() != 0 && userBookDTO.getBookId() != 0){
            if (userBookService.getUserBookByIdCombined(userBookDTO.getUserId(), userBookDTO.getBookId()).isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(
                    modelMapper.map(userBookService.insertOrUpdate(modelMapper.map(userBookDTO, UserBook.class)), UserBookDTO.class), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserBookDTO> putUserBook(@RequestBody UserBookDTO userBookDTO) throws Exception {
        Optional<UserBook> userBook = userBookService.getUserBookById(userBookDTO.getId());
        if (userBook.isPresent()){
            userBook.get().setRating(userBookDTO.getRating());
            return new ResponseEntity<>(
                    modelMapper.map(userBookService.insertOrUpdate(userBook.get()),UserBookDTO.class)
                    , HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUserBookDTO> getUserBookById(@PathVariable(name = "id") int id) throws Exception {
        Optional<UserBook> userBook = userBookService.getUserBookById(id);
        if (userBook.isPresent()) {
            ResponseUserBookDTO responseUserBookDTO = new ResponseUserBookDTO(
                    userBook.get().getId(),
                    modelMapper.map(userBook.get().getUser(), UserDTO.class),
                    modelMapper.map(userBook.get().getBook(), BookDTO.class),
                    userBook.get().getRating(),
                    userBook.get().getCreationDate(),
                    userBook.get().getUpdatedDate()
            );
            responseUserBookDTO.getBook().setGenreNames(userBook.get().getBook().getGenres().stream().map(Genre::getName).collect(Collectors.toSet()));
            return new ResponseEntity<>(responseUserBookDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{userid}/{bookid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserBookDTO> getUserBookByUserAndBookId(@PathVariable(name = "userid") int userid, @PathVariable(name = "bookid") int bookid) throws Exception {
        Optional<UserBook> userBook = userBookService.getUserBookByIdCombined(userid, bookid);
        if (userBook.isPresent()) {
            return new ResponseEntity<>(
                    modelMapper.map(userBook.get(), UserBookDTO.class)
                    , HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteUserBookById(@PathVariable(name = "id") int id) throws Exception {
        return new ResponseEntity<>(
                "Deleted UserBook with ID: " + id + " - " + userBookService.deleteUserBookById(id)
                , HttpStatus.OK);
    }

    @DeleteMapping(value = "/{userid}/{bookid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteUserBookByUserAndBookId(@PathVariable(name = "userid") int userid, @PathVariable(name = "bookid") int bookid) throws Exception {
        return new ResponseEntity<>(
                "Deleted UserBook with user ID: " + userid + " And Book ID: " + bookid + " - " + userBookService.deleteUserBookByIdCombined(userid,bookid)
                , HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserBookDTO>> getUserBooks(){
        return new ResponseEntity<>(
                userBookService.getAllUserBooks().stream()
                        .map(UserBook -> modelMapper.map(UserBook, UserBookDTO.class))
                        .collect(Collectors.toList())
                ,HttpStatus.OK);
    }

}
