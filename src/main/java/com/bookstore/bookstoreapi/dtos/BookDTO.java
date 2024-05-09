package com.bookstore.bookstoreapi.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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

    @JsonIgnore
    private Set<Integer> userBookIds = new HashSet<>();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<String> genreNames = new HashSet<>();

    public BookDTO(int id, String title, String author, Set<Integer> userBookIds, Set<String> genreNames) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.userBookIds = userBookIds;
        this.genreNames = genreNames;
    }
}
