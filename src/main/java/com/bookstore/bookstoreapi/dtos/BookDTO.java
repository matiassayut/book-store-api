package com.bookstore.bookstoreapi.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class BookDTO {

    private int id;
    private String title;
    private String author;
    private Set<Integer> userBookIds = new HashSet<>();
    private Set<Integer> genreIds = new HashSet<>();

    public BookDTO(int id, String title, String author, Set<Integer> userBookIds, Set<Integer> genreIds) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.userBookIds = userBookIds;
        this.genreIds = genreIds;
    }
}
