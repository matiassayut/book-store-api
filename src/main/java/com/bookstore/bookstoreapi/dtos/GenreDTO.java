package com.bookstore.bookstoreapi.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class GenreDTO {

    private int id;
    private String name;
    private Set<Integer> bookIds = new HashSet<>();

    // Constructor con todos los campos
    public GenreDTO(int id, String name, Set<Integer> bookIds) {
        this.id = id;
        this.name = name;
        this.bookIds = bookIds;
    }
}
