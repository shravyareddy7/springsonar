package com.DTO.service;

import com.DTO.dto.BookDTO;
import com.DTO.model.Book;
import com.DTO.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class BookService {
    private final BookRepository bookRepository;
    private final List<BookDTO> bookDTOs = new ArrayList<>();

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(Book book) {
        bookRepository.addBook(book);
        createBookDTO(book);
    }

    private void createBookDTO(Book book) {
        bookDTOs.add(new BookDTO(book.getTitle(), book.getAuthor(), book.getGenre()));
    }

    public List<BookDTO> getBooks() {
        return bookDTOs;
    }

    public BookDTO getBookByTitle(String title) {
        return bookDTOs.stream().filter(book -> Objects.equals(book.getTitle(), title)).findFirst().orElse(null);
    }
}
