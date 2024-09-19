package com.DTO.model;

import lombok.Data;

@Data
public class Book {
    private String title;
    private String author;
    private String genre;
    private int pages;
    private String isbn;
}
