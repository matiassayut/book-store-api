package com.bookstore.bookstoreapi.services.implementation;

import com.bookstore.bookstoreapi.entities.Book;
import com.bookstore.bookstoreapi.entities.Genre;
import com.bookstore.bookstoreapi.repositories.IGenreRepository;
import com.bookstore.bookstoreapi.services.IGenreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService implements IGenreService {

    @Autowired
    private IGenreRepository genreRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public Genre insertOrUpdate(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Optional<Genre> getGenreById(int id) throws Exception {
        return genreRepository.findById(id);
    }

    @Override
    public Optional<Genre> getGenreByName(String name) throws Exception {
        return genreRepository.findByName(name);
    }

    @Override
    public boolean deleteGenreById(int id) {
        try{
            genreRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

}
