package com.testproject.libraryservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.sql.Timestamp;

@Setter
@Getter
@ToString
@Entity
@Table(name = "bookclaims", schema = "public")
public class BookClaim {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator")
    @SequenceGenerator(name = "sequence-generator", sequenceName = "bookclaims_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "book_id", nullable = false)
    private Long bookId;
    @Column(name = "claim_date")
    private Timestamp claimDate;
    @Column(name = "return_date")
    private Timestamp returnDate;
}
