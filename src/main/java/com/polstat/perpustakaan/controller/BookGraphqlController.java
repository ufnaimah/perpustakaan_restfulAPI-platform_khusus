package com.polstat.perpustakaan.controller;

import com.polstat.perpustakaan.dto.BookDto;
import com.polstat.perpustakaan.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class BookGraphqlController {

    @Autowired
    private BookService bookService;

    // Resolver untuk query "books"
    @QueryMapping
    public List<BookDto> books() {
        return bookService.getBooks();
    }

    // Resolver untuk query "bookById"
    @QueryMapping
    public BookDto bookById(@Argument Long id) {
        // Anda perlu menambahkan method getBookById di service Anda
        // Untuk saat ini, kita bisa gunakan Optional
        return bookService.getBooks().stream()
                .filter(book -> book.getId().equals(id)).findFirst().orElse(null);
    }

    // Resolver untuk mutasi "createBook"
    @MutationMapping
    public BookDto createBook(@Argument String title, @Argument String author, @Argument String description) {
        BookDto bookDto = BookDto.builder()
                .title(title)
                .author(author)
                .description(description)
                .build();
        bookService.createBook(bookDto);
        return bookDto;
    }

    // Dan seterusnya untuk update dan delete...
}