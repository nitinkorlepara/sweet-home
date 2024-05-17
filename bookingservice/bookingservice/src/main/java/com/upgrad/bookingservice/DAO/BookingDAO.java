package com.upgrad.bookingservice.DAO;

import com.upgrad.bookingservice.model.BookingInfoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface BookingDAO extends JpaRepository<BookingInfoEntity,Integer> {


}
