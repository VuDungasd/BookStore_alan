package com.example.ecomerce_bookstore.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private Long authorId;
    private String name;
    private String status;
    private String image;
    private String description;
    private String address;
    private Date birthday;
}
