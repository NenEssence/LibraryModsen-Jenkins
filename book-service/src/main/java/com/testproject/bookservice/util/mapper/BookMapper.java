package com.testproject.bookservice.util.mapper;

import com.testproject.bookservice.dto.BookRequest;
import com.testproject.bookservice.dto.BookResponse;
import com.testproject.bookservice.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper {
    BookResponse toBookResponse(Book book);

    Book toBook(BookRequest bookRequest);
}
