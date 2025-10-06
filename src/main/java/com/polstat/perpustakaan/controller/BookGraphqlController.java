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

    @QueryMapping
    public List<BookDto> books() {
        return bookService.getBooks();
    }

    @QueryMapping
    public BookDto bookById(@Argument Long id) {
        return bookService.getBook(id);
    }

    // --- PERBAIKAN PADA CREATEBOOK ---
    @MutationMapping
    public BookDto createBook(@Argument String title, @Argument String author, @Argument String description) {
        BookDto bookDto = BookDto.builder()
                .title(title)
                .author(author)
                .description(description)
                .build();
        // Kembalikan hasil dari service, bukan bookDto lokal
        return bookService.createBook(bookDto);
    }

    // --- PERBAIKAN PADA UPDATEBOOK ---
    @MutationMapping
    public BookDto updateBook(@Argument Long id, @Argument String title, @Argument String author, @Argument String description) {
        BookDto bookDto = BookDto.builder()
                .id(id) // Sertakan ID untuk operasi update
                .title(title)
                .author(author)
                .description(description)
                .build();
        return bookService.updateBook(bookDto);
    }

    @MutationMapping
    public boolean deleteBook(@Argument Long id) {
        bookService.deleteBook(id);
        return true;
    }
}