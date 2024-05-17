package com.upgrad.bookingservice.controller;

import com.upgrad.bookingservice.DAO.BookingVO;
import com.upgrad.bookingservice.DAO.PaymentVO;
import com.upgrad.bookingservice.model.BookingInfoEntity;
import com.upgrad.bookingservice.service.BookingServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BookingController {

    @Autowired
    BookingServiceImpl bookingService;

    @PostMapping(value="booking",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingVO> makeBooking(@RequestBody BookingVO bookingVO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.makeBooking(bookingVO));
    }

    @PostMapping(value="booking/{bookingId}/transaction", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String makePayment(@RequestBody PaymentVO paymentVO,
                                                 @PathVariable(name = "bookingId") int bookingId) {

        return "makepayment";//ResponseEntity.status(HttpStatus.CREATED).body(bookingService.makeBooking(paymentVO));
    }



}
