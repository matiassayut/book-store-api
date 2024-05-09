package com.bookstore.bookstoreapi.controllers;

import com.bookstore.bookstoreapi.dtos.GenreDTO;
import com.bookstore.bookstoreapi.entities.Genre;
import com.bookstore.bookstoreapi.services.IGenreService;
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
@RequestMapping(path = "api/v1/genre")
public class GenreRestController {

    @Autowired
    private IGenreService genreService;

    private ModelMapper modelMapper = new ModelMapper();

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenreDTO> postGenre(@RequestBody GenreDTO genreDTO){
        return new ResponseEntity<>(
                modelMapper.map(genreService.insertOrUpdate(modelMapper.map(genreDTO, Genre.class)), GenreDTO.class), HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenreDTO> putGenre(@RequestBody GenreDTO genreDTO) throws Exception {
        Optional<Genre> genre = genreService.getGenreById(genreDTO.getId());
        if (genre.isPresent()){
            genre.get().setName(genreDTO.getName());
            return new ResponseEntity<>(
                    modelMapper.map(genreService.insertOrUpdate(genre.get()),GenreDTO.class)
                    , HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenreDTO> getGenreById(@PathVariable(name = "id") int id) throws Exception {
        Optional<Genre> genre = genreService.getGenreById(id);
        if (genre.isPresent()) {
            return new ResponseEntity<>(
                    modelMapper.map(genre.get(), GenreDTO.class)
                    , HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteGenreById(@PathVariable(name = "id") int id) throws Exception {
        return new ResponseEntity<>(
                "Deleted Genre with ID: " + id + " - " + genreService.deleteGenreById(id)
                , HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenreDTO>> getGenres(){
        return new ResponseEntity<>(
                genreService.getAllGenres().stream()
                    .map(Genre -> modelMapper.map(Genre, GenreDTO.class))
                    .collect(Collectors.toList())
                ,HttpStatus.OK);
    }

}
