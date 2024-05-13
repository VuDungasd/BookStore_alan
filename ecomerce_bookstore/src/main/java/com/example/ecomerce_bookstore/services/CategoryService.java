package com.example.ecomerce_bookstore.services;


import com.example.ecomerce_bookstore.entities.Category;
import com.example.ecomerce_bookstore.payloads.CategoryDTO;
import com.example.ecomerce_bookstore.payloads.CategoryResponse;

public interface CategoryService {

    CategoryDTO createCategory(Category category);

    CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO updateCategory(Category category, Long categoryId);

    String deleteCategory(Long categoryId);
}
