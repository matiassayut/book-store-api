package com.bookstore.bookstoreapi.services.implementation;

import com.bookstore.bookstoreapi.dtos.BookDTO;
import com.bookstore.bookstoreapi.entities.Book;
import com.bookstore.bookstoreapi.entities.Genre;
import com.bookstore.bookstoreapi.repositories.IBookRepository;
import com.bookstore.bookstoreapi.services.IBookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService {

    @Autowired
    private IBookRepository bookRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public Book insertOrUpdate(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> getBookById(int id) throws Exception {
        return bookRepository.findById(id);
    }

    @Override
    public boolean deleteBookById(int id) {
        try{
            bookRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<BookDTO> getAllBooksByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthor(author);
        List<BookDTO> bookDTOs = new ArrayList<>();

        for (Book book : books) {
            BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
            bookDTO.setGenreNames(book.getGenres().stream().map(Genre::getName).collect(Collectors.toSet()));
            bookDTOs.add(bookDTO);
        }

        return bookDTOs;
    }

    @Override
    public List<BookDTO> getAllBooksByGenreId(int id) {
        List<Book> books = bookRepository.findByGenreId(id);
        List<BookDTO> bookDTOs = new ArrayList<>();

        for (Book book : books) {
            BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
            bookDTO.setGenreNames(book.getGenres().stream().map(Genre::getName).collect(Collectors.toSet()));
            bookDTOs.add(bookDTO);
        }

        return bookDTOs;
    }

    @Override
    public List<BookDTO> getAllBooksByUserId(int id) {
        List<Book> books = bookRepository.findByUserId(id);
        List<BookDTO> bookDTOs = new ArrayList<>();

        for (Book book : books) {
            BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
            bookDTO.setGenreNames(book.getGenres().stream().map(Genre::getName).collect(Collectors.toSet()));
            bookDTOs.add(bookDTO);
        }

        return bookDTOs;
    }

}
