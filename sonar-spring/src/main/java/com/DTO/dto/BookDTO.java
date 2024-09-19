package com.DTO.dto;

public class BookDTO {
    private String title;
    private String author;
    private String genre;

    public BookDTO() {
    }

    public BookDTO(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\nAuthor: " + author + "\nGenre: " + genre;
    }
}
