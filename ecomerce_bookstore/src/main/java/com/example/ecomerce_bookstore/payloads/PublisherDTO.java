package com.example.ecomerce_bookstore.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDTO {
    private Long publisherId;
    private String publisherName;
    private String status;
    private String description;
    private String address;
}
