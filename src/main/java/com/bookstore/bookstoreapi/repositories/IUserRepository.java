package com.bookstore.bookstoreapi.repositories;

import com.bookstore.bookstoreapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface IUserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT DISTINCT u FROM User u JOIN u.userBooks ub JOIN ub.book b WHERE b.id = :bookId")
    List<User> findByBookId(@Param("bookId") int bookId);

}
