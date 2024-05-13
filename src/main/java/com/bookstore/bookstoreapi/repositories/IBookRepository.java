package com.bookstore.bookstoreapi.repositories;

import com.bookstore.bookstoreapi.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("bookRepository")
public interface IBookRepository extends JpaRepository<Book, Integer> {

    public abstract List<Book> findByAuthor(String name);

    @Query("SELECT b FROM Book b JOIN b.genres g WHERE g.id = :genreId")
    List<Book> findByGenreId(@Param("genreId") int genreId);

    @Query("SELECT DISTINCT b FROM Book b JOIN b.userBooks ub JOIN ub.user u WHERE u.id = :userId")
    List<Book> findByUserId(@Param("userId") int userId);

}
