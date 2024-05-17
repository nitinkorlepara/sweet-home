package com.upgrad.bookingservice.DAO;

import com.upgrad.bookingservice.model.BookingInfoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface BookingDAO extends JpaRepository<BookingInfoEntity,Integer> {

    @Modifying
    @Transactional
    @Query("update BookingInfoEntity b set b.transactionId = :transactionId where b.bookingId = :bookingId")
    int updateTransaction(@Param("transactionId") int transactionId, @Param("bookingId") int bookingId);
}
