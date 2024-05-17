package com.upgrad.bookingservice.controller;

import com.upgrad.bookingservice.DAO.BookingVO;
import com.upgrad.bookingservice.DAO.PaymentVO;
import com.upgrad.bookingservice.model.BookingInfoEntity;
import com.upgrad.bookingservice.service.BookingServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class BookingController {

    @Autowired
    BookingServiceImpl bookingService;

    @Autowired
    RestTemplate restTemplate;

    @PostMapping(value="/booking",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingVO> makeBooking(@RequestBody BookingVO bookingVO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.makeBooking(bookingVO));
    }

    @PostMapping(value="/booking/{bookingId}/transaction", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> makePayment(@RequestBody PaymentVO paymentVO,
                                                 @PathVariable(name = "bookingId") int bookingId) {

        if (!"UPI".equals(paymentVO.getPaymentMode()) && !"CARD".equals(paymentVO.getPaymentMode())) {
            Map<String, String> errors = new HashMap<>();
            String fieldName = "message";
            String errorMessage = "Invalid mode of payment";
            errors.put(fieldName, errorMessage);
            errors.put("statusCode", "400");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        if(bookingId!=paymentVO.getBookingId() && !bookingService.isBookingExists(bookingId)){
            Map<String, String> errors = new HashMap<>();
            String fieldName = "message";
            String errorMessage = " Invalid Booking Id ";
            errors.put(fieldName, errorMessage);
            errors.put("statusCode", "400");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
//        HttpEntity<PaymentVO> request = new HttpEntity<>(paymentVO);
//        ResponseEntity<Integer> response = restTemplate
//                .exchange("http://localhost:8083/payment/transaction", HttpMethod.POST, request, Integer.class);
         int transactionId = 594;//response.getBody().intValue();
        BookingVO vo = bookingService.updateTransaction(transactionId,bookingId);
        String message = "Booking confirmed for user with aadhaar number: "
                + vo.getAadharNumber()
                +    "    |    "
                + "Here are the booking details:    " + vo.toString();

        System.out.println(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(vo);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = "message";
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            errors.put("statusCode","400");
        });
        return errors;
    }



}
