package com.bookstore.bookstoreapi.repositories;

import com.bookstore.bookstoreapi.entities.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userBookRepository")
public interface IUserBookRepository extends JpaRepository<UserBook, Integer> {
}
