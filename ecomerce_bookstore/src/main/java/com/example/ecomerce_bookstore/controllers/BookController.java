package com.example.ecomerce_bookstore.controllers;

import java.io.IOException;

import com.example.ecomerce_bookstore.config.AppConstants;
import com.example.ecomerce_bookstore.entities.Book;
import com.example.ecomerce_bookstore.payloads.BookDTO;
import com.example.ecomerce_bookstore.payloads.BookResponse;
import com.example.ecomerce_bookstore.services.BookService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
// sử dụng thư viện swagger được sử dụng để tạo và quản lý tài liệu API.
@SecurityRequirement(name = "E-Commerce Application")
public class BookController {

    @Autowired
    private BookService bookService;

    // add book
    @PostMapping("/admin/categories/{categoryId}/book")
    public ResponseEntity<BookDTO> addBook(@Valid @RequestBody Book book, @PathVariable Long categoryId) {

        BookDTO savedBook = bookService.addBook(categoryId, book);

        return new ResponseEntity<BookDTO>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping("/public/books")
    public ResponseEntity<BookResponse> getAllBooks(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BOOKS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

        BookResponse bookResponse = bookService.getAllBooks(pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<BookResponse>(bookResponse, HttpStatus.FOUND);
    }

    @GetMapping("/public/categories/{categoryId}/books")
    public ResponseEntity<BookResponse> getBooksByCategory(@PathVariable Long categoryId,
                                                                 @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                 @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                 @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BOOKS_BY, required = false) String sortBy,
                                                                 @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

        BookResponse bookResponse = bookService.searchByCategory(categoryId, pageNumber, pageSize, sortBy,
                sortOrder);

        return new ResponseEntity<BookResponse>(bookResponse, HttpStatus.FOUND);
    }

    @GetMapping("/public/books/keyword/{keyword}")
    public ResponseEntity<BookResponse> getBooksByKeyword(@PathVariable String keyword,
                                                                @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BOOKS_BY, required = false) String sortBy,
                                                                @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

        BookResponse bookResponse = bookService.searchBookByKeyword(keyword, pageNumber, pageSize, sortBy,
                sortOrder);

        return new ResponseEntity<BookResponse>(bookResponse, HttpStatus.FOUND);
    }

    @PutMapping("/public/books/{bookId}")
    public ResponseEntity<BookDTO> updateBook(@RequestBody Book book,
                                                    @PathVariable Long bookId) {
        BookDTO updatedBook = bookService.updateBook(bookId, book);

        return new ResponseEntity<BookDTO>(updatedBook, HttpStatus.OK);
    }

    @PutMapping("/admin/books/{bookId}/image")
    public ResponseEntity<BookDTO> updateBookImage(@PathVariable Long bookId, @RequestParam("image") MultipartFile image) throws IOException {
        BookDTO updatedBook = bookService.updateBookImage(bookId, image);

        return new ResponseEntity<BookDTO>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/admin/books/{bookId}")
    public ResponseEntity<String> deleteBookByCategory(@PathVariable Long bookId) {
        String status = bookService.deleteBook(bookId);

        return new ResponseEntity<String>(status, HttpStatus.OK);
    }

}
