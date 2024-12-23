package com.testproject.libraryservice.mapper;

import com.testproject.libraryservice.dto.BookClaimRequest;
import com.testproject.libraryservice.dto.BookClaimResponse;
import com.testproject.libraryservice.model.BookClaim;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookClaimMapper {
    BookClaimResponse toBookClaimResponse(BookClaim bookClaim);

    BookClaim toBookClaim(BookClaimRequest bookClaimRequest);
}
