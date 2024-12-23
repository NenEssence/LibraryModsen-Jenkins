package com.testproject.libraryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookClaimResponse {
    private Long bookId;
    private Timestamp claimDate;
    private Timestamp returnDate;
}
