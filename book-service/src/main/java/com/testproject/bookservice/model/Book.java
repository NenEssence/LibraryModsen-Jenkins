package com.testproject.bookservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books", schema = "public")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator")
    @SequenceGenerator(name = "sequence-generator", sequenceName = "books_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "isbn", nullable = false)
    private String isbn;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "genre", nullable = false)
    private String genre;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "author", nullable = false)
    private String author;
}
