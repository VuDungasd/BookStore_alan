package com.example.ecomerce_bookstore.repositories;


import com.example.ecomerce_bookstore.entities.Book;
import com.example.ecomerce_bookstore.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface CartItemRepo extends JpaRepository<CartItem, Long>{

    // tim book tu id
    @Query("SELECT ci.book FROM CartItem ci WHERE ci.book.id = ?1")
    Book findBookById(Long bookId);


    // find cartItem by bookID and cartID
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = ?1 AND ci.book.id = ?2")
    CartItem findCartItemByBookIdAndCartId(Long cartId, Long bookId);


    // su dung @Anotation Modifying để sửa đọc sửa database
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = ?1 AND ci.book.id = ?2")
    void deleteCartItemByBookIdAndCartId(Long bookId, Long cartId);
}
