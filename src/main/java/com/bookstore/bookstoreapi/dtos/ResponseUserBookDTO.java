package com.bookstore.bookstoreapi.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor
public class ResponseUserBookDTO {

    private int id;
    private UserDTO user;
    private BookDTO book;
    private LocalDate creationDate;
    private LocalDate updatedDate;

    public ResponseUserBookDTO(int id, UserDTO user, BookDTO book, LocalDate creationDate, LocalDate updatedDate) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.creationDate = creationDate;
        this.updatedDate = updatedDate;
    }
}
