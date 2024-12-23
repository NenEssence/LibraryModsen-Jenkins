package com.testproject.libraryservice.repository;

import com.testproject.libraryservice.model.BookClaim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface LibraryRepository extends JpaRepository<BookClaim, Long> {

    @Query("SELECT b FROM BookClaim b \n WHERE b.returnDate < :currentDate OR b.returnDate IS NULL")
    Page<BookClaim> findAvailableBookCliams(@Param("currentDate") LocalDateTime currentDate, Pageable pageable);

    Optional<BookClaim> getBookClaimByBookId(Long id);

    @Transactional
    void deleteBookClaimByBookId(Long id);
}
