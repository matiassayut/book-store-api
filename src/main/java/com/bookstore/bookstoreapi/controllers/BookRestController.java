package com.bookstore.bookstoreapi.controllers;

import com.bookstore.bookstoreapi.dtos.BookDTO;
import com.bookstore.bookstoreapi.dtos.GenreDTO;
import com.bookstore.bookstoreapi.entities.Book;
import com.bookstore.bookstoreapi.entities.Genre;
import com.bookstore.bookstoreapi.services.IBookService;
import com.bookstore.bookstoreapi.services.IGenreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/books")
public class BookRestController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private IGenreService genreService;

    private ModelMapper modelMapper = new ModelMapper();

    //Creation of Book, by JSON body of "title", "author" and "genreNames" (List, can be integer of Genre id or String of genre names)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> postBook(@RequestBody BookDTO bookDTO) throws Exception {
        if (bookDTO.getId() != 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Book book = modelMapper.map(bookDTO, Book.class);
        Set<Genre> genres = new HashSet<>();
        for (String genreName : bookDTO.getGenreNames()) {
            Optional<Genre> genre = genreService.getGenreByName(genreName);
            if (genre.isPresent()) {
                genres.add(genre.get());
            } else {
                try {
                    int genreId = Integer.parseInt(genreName);
                    Optional<Genre> genreById = genreService.getGenreById(genreId);
                    if (genreById.isPresent()) {
                        genres.add(genreById.get());
                    } else {
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }
                } catch (NumberFormatException ex) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        book.setGenres(genres);
        bookService.insertOrUpdate(book);
        return new ResponseEntity<>(
                modelMapper.map(book, BookDTO.class), HttpStatus.CREATED);
    }

    //Modification of Book, by JSON body of "title", "author" or "genreNames" (List, can be integer of Genre id or String of genre names)
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> putBook(@RequestBody BookDTO bookDTO) throws Exception {
        Optional<Book> book = bookService.getBookById(bookDTO.getId());
        if (book.isPresent()){
            book.get().setAuthor(bookDTO.getAuthor());
            book.get().setTitle(bookDTO.getTitle());

            Set<Genre> genres = new HashSet<>();
            for (String genreName : bookDTO.getGenreNames()) {
                Optional<Genre> genre = genreService.getGenreByName(genreName);
                if (genre.isPresent()) {
                    genres.add(genre.get());
                } else {
                    try {
                        int genreId = Integer.parseInt(genreName);
                        Optional<Genre> genreById = genreService.getGenreById(genreId);
                        if (genreById.isPresent()) {
                            genres.add(genreById.get());
                        } else {
                            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                        }
                    } catch (NumberFormatException ex) {
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }
                }
            }
            book.get().setGenres(genres);
            return new ResponseEntity<>(
                    modelMapper.map(bookService.insertOrUpdate(book.get()),BookDTO.class), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> getBookById(@PathVariable(name = "id") int id) throws Exception {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            return new ResponseEntity<>(
                    modelMapper.map(book.get(), BookDTO.class)
                    , HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteBookById(@PathVariable(name = "id") int id) throws Exception {
        return new ResponseEntity<>(
                "Deleted Book with ID: " + id + " - " + bookService.deleteBookById(id)
                , HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> getBooks(){
        List<Book> books = bookService.getAllBooks();
        List<BookDTO> bookDTOs = new ArrayList<>();

        for (Book book : books) {
            BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
            bookDTO.setGenreNames(book.getGenres().stream().map(Genre::getName).collect(Collectors.toSet()));
            bookDTOs.add(bookDTO);
        }

        return new ResponseEntity<>(bookDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/author/{author}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> getAllBooksByAuthor(@PathVariable(name = "author") String author) {
        return new ResponseEntity<>(bookService.getAllBooksByAuthor(author),HttpStatus.OK);
    }

    @GetMapping(value = "/genre/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> getAllBooksByGenreId(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(bookService.getAllBooksByGenreId(id),HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> getAllBooksByUserId(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(bookService.getAllBooksByUserId(id),HttpStatus.OK);
    }


}
