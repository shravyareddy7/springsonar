package com.DTO.repository;

import com.DTO.model.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class BookRepository {
    private final List<Book> books = new ArrayList<>();

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public Book getBookByTitle(String title) {
        return books.stream().filter(book -> Objects.equals(book.getTitle(), title)).findFirst().orElse(null);
    }
}
