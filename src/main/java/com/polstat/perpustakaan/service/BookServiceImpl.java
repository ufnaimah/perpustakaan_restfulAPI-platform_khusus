package com.polstat.perpustakaan.service;

import com.polstat.perpustakaan.dto.BookDto;
import com.polstat.perpustakaan.entity.Book;
import com.polstat.perpustakaan.mapper.BookMapper;
import com.polstat.perpustakaan.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = bookRepository.save(BookMapper.mapToBook(bookDto));
        return BookMapper.mapToBookDto(book);
    }

    @Override
    public List<BookDto> getBooks() {
        return bookRepository.findAll().stream()
                .map(BookMapper::mapToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        return BookMapper.mapToBookDto(book);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        // Pastikan buku ada sebelum update
        bookRepository.findById(bookDto.getId())
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookDto.getId()));
        Book book = bookRepository.save(BookMapper.mapToBook(bookDto));
        return BookMapper.mapToBookDto(book);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

}