package com.example.ecomerce_bookstore.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.example.ecomerce_bookstore.entities.Book;
import com.example.ecomerce_bookstore.entities.Cart;
import com.example.ecomerce_bookstore.entities.Category;
import com.example.ecomerce_bookstore.exceptions.APIException;
import com.example.ecomerce_bookstore.exceptions.ResourceNotFoundException;
import com.example.ecomerce_bookstore.payloads.BookDTO;
import com.example.ecomerce_bookstore.payloads.BookResponse;
import com.example.ecomerce_bookstore.payloads.CartDTO;
import com.example.ecomerce_bookstore.repositories.BookRepo;
import com.example.ecomerce_bookstore.repositories.CartRepo;
import com.example.ecomerce_bookstore.repositories.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import jakarta.transaction.Transactional;

@Transactional
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private CategoryRepo categoryRepo;
//    @Autowired
//    private CartRepo cartRepo;
//    @Autowired
//    private CartService cartService;
//    @Autowired
//    private FileService fileService;
    @Autowired
    private ModelMapper modelMapper;
    @Value("${project.image}")
    private String path;

    @Override
    public BookDTO addBook(Long categoryId, Book book) {

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));

        boolean isBookNotPresent = true;

        List<Book> books = category.getBooks();

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getBookName().equals(book.getBookName())
                    && books.get(i).getDescription().equals(book.getDescription())) {

                isBookNotPresent = false;
                break;
            }
        }

        if (isBookNotPresent) {
            book.setImage("default.png");

            book.setCategory(category);

            double specialPrice = book.getPrice() - ((book.getDiscount() * 0.01) * book.getPrice());
            book.setSpecialPrice(specialPrice);

            Book savedBook = bookRepo.save(book);

            return modelMapper.map(savedBook, BookDTO.class);
        } else {
            throw new APIException("Book already exists !!!");
        }
    }

    @Override
    public BookResponse getAllBooks(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Book> pageBooks = bookRepo.findAll(pageDetails);

        List<Book> books = pageBooks.getContent();

        List<BookDTO> bookDTOs = books.stream().map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());

        BookResponse bookResponse = new BookResponse();

        bookResponse.setContent(bookDTOs);
        bookResponse.setPageNumber(pageBooks.getNumber());
        bookResponse.setPageSize(pageBooks.getSize());
        bookResponse.setTotalElements(pageBooks.getTotalElements());
        bookResponse.setTotalPages(pageBooks.getTotalPages());
        bookResponse.setLastPage(pageBooks.isLast());
        return bookResponse;
    }

    @Override
    public BookResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return null;
    }

    @Override
    public BookDTO updateBook(Long bookId, Book book) {
        Book bookFromDB = bookRepo.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "bookId", bookId));

        if (bookFromDB == null) {
            throw new APIException("Book not found with bookId: " + bookId);
        }

        book.setImage(bookFromDB.getImage());
        book.setBookId(bookId);
        book.setCategory(bookFromDB.getCategory());

        double specialPrice = book.getPrice() - ((book.getDiscount() * 0.01) * book.getPrice());
        book.setSpecialPrice(specialPrice);

        Book savedBook = bookRepo.save(book);

//        List<Cart> carts = cartRepo.findCartsByBookId(bookId);

//        List<CartDTO> cartDTOs = carts.stream().map(cart -> {
//            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
//
//            List<BookDTO> books = cart.getCartItems().stream()
//                    .map(p -> modelMapper.map(p.getBook(), BookDTO.class)).collect(Collectors.toList());
//
//            cartDTO.setBooks(books);
//
//            return cartDTO;

//        }).collect(Collectors.toList());

//        cartDTOs.forEach(cart -> cartService.updateBookInCarts(cart.getCartId(), bookId));

        return modelMapper.map(savedBook, BookDTO.class);
    }

    @Override
    public BookDTO updateBookImage(Long bookId, MultipartFile image) throws IOException {
        return null;
    }

    @Override
    public BookResponse searchBookByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return null;
    }

    @Override
    public String deleteBook(Long bookId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "bookId", bookId));
        bookRepo.delete(book);

        return "Book with bookId: " + bookId + " deleted successfully !!!";
    }


}