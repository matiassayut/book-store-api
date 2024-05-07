package com.bookstore.bookstoreapi.repositories;

import com.bookstore.bookstoreapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface IUserRepository extends JpaRepository<User, Integer> {
}
