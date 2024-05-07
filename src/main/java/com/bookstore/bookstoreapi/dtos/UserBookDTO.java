package com.bookstore.bookstoreapi.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor
public class UserBookDTO {

    private int id;
    private int userId;
    private int bookId;
    private int rating;
    private LocalDate creationDate;
    private LocalDate updatedDate;

    public UserBookDTO(int id, int userId, int bookId, int rating, LocalDate creationDate, LocalDate updatedDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.rating = rating;
        this.creationDate = creationDate;
        this.updatedDate = updatedDate;
    }
}
