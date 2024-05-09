package com.bookstore.bookstoreapi.repositories;

import com.bookstore.bookstoreapi.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("genreRepository")
public interface IGenreRepository extends JpaRepository<Genre, Integer> {

    public abstract Optional<Genre> findByName(String name);

}
