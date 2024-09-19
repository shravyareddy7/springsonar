package com.DTO.controller;

import com.DTO.service.BookService;
import com.DTO.dto.BookDTO;
import com.DTO.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public void addBook(Book book) {
        bookService.addBook(book);
    }

    public List<BookDTO> getAllBooks() {
        return bookService.getBooks();
    }

    public BookDTO getBookByTitle(String title) {
        return bookService.getBookByTitle(title);
    }
}
