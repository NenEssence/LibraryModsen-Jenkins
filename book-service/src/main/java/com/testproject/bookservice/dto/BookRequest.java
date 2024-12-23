package com.testproject.bookservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookRequest {
    @NotBlank(message = "Isbn is mandatory")
    @Pattern(regexp = "^97[89]\\d{10}$", message = "Incorrect ISBN-13 format")
    private String isbn;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Genre is mandatory")
    private String genre;
    @NotBlank(message = "Description is mandatory")
    private String description;
    @NotBlank(message = "Author is mandatory")
    private String author;
}
