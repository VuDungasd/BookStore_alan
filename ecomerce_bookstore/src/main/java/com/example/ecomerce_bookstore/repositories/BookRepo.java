package com.example.ecomerce_bookstore.repositories;

import com.example.ecomerce_bookstore.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    Page<Book> findByBookNameLike(String keyword, Pageable pageDetails);

}
