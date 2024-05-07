package com.bookstore.bookstoreapi.repositories;

import com.bookstore.bookstoreapi.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("bookRepository")
public interface IBookRepository extends JpaRepository<Book, Integer> {
}
