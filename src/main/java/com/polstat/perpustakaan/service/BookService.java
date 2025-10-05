package com.polstat.perpustakaan.service;

import com.polstat.perpustakaan.dto.BookDto;
import java.util.List;

public interface BookService {
    // Mengembalikan DTO untuk mendapatkan data buku yang baru dibuat
    BookDto createBook(BookDto bookDto);

    List<BookDto> getBooks();

    // Metode baru untuk mengambil satu buku berdasarkan ID
    BookDto getBook(Long id);

    // Mengembalikan DTO untuk mendapatkan data buku yang diperbarui
    BookDto updateBook(BookDto bookDto);

    void deleteBook(Long id); // Diubah agar lebih sederhana, hanya butuh ID
}