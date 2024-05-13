package com.example.ecomerce_bookstore.services;

import com.example.ecomerce_bookstore.payloads.CartDTO;

import java.util.List;


public interface CartService {

    CartDTO addBookToCart(Long cartId, Long bookId, Integer quantity);

    List<CartDTO> getAllCarts();

    CartDTO getCart(String emailId, Long cartId);

    CartDTO updateBookQuantityInCart(Long cartId, Long bookId, Integer quantity);

    void updateBookInCarts(Long cartId, Long bookId);

    String deleteBookFromCart(Long cartId, Long bookId);

}
