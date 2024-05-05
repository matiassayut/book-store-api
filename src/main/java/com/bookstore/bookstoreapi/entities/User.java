package com.bookstore.bookstoreapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter @Setter @NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique=true, nullable=false, length=30)
    private String username;

    @Email
    @Column(unique=true, nullable = false, length = 70)
    private String email;

    @OneToMany(mappedBy = "user")
    private Set<UserBook> userBooks = new HashSet<>();

}
