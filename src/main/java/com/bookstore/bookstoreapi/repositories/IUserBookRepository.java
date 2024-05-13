package com.bookstore.bookstoreapi.repositories;

import com.bookstore.bookstoreapi.entities.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userBookRepository")
public interface IUserBookRepository extends JpaRepository<UserBook, Integer> {

    @Query("SELECT ub FROM UserBook ub WHERE ub.user.id = :userId AND ub.book.id = :bookId")
    public abstract Optional<UserBook> findByUserIdAndBookId(@Param("userId") int userId, @Param("bookId") int bookId);

}
