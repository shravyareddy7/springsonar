package com.DTO;

import com.DTO.controller.BookController;
import com.DTO.model.Book;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.DTO");
        context.refresh();

        BookController bookController = context.getBean(BookController.class);

        Book book1 = new Book();
        book1.setTitle("Alchemist");
        book1.setAuthor("Paulo Coelho");
        book1.setGenre("Fiction");
        book1.setPages(180);
        book1.setIsbn("9780743273565");

        bookController.addBook(book1);

        System.out.println("All Books: ");
        bookController.getAllBooks().forEach(System.out::println);

        System.out.println("\nFetching Book by Title: 'Alchemist'");
        System.out.println(bookController.getBookByTitle("Alchemist"));


        context.close();
    }
}
