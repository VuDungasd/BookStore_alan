package com.example.ecomerce_bookstore.repositories;

import com.example.ecomerce_bookstore.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

}

