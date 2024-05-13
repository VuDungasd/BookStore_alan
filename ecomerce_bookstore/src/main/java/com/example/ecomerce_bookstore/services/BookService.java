package com.example.ecomerce_bookstore.services;

import java.io.IOException;

import com.example.ecomerce_bookstore.entities.Book;
import com.example.ecomerce_bookstore.payloads.BookDTO;
import com.example.ecomerce_bookstore.payloads.BookResponse;
import org.springframework.web.multipart.MultipartFile;


public interface BookService {

    BookDTO addBook(Long categoryId, Book book);

    BookResponse getAllBooks(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    BookResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy,
                                     String sortOrder);

    BookDTO updateBook(Long bookId, Book book);

    BookDTO updateBookImage(Long bookId, MultipartFile image) throws IOException;

    BookResponse searchBookByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy,
                                           String sortOrder);

    String deleteBook(Long bookId);

}
