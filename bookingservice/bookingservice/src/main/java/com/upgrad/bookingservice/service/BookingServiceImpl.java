package com.upgrad.bookingservice.service;

import com.upgrad.bookingservice.DAO.BookingDAO;
import com.upgrad.bookingservice.DAO.BookingVO;
import com.upgrad.bookingservice.model.BookingInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDAO bookingDAO;

    @Override
    public BookingVO findBookingById(int id) {
        Optional<BookingInfoEntity> bookingInfoEntity = bookingDAO.findById(id);
        if(bookingInfoEntity.isPresent()){
            BookingVO  bookingVO= new BookingVO();
            bookingVO.setId(bookingInfoEntity.get().getBookingId());
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            bookingVO.setNumOfRooms(bookingInfoEntity.get().getNumOfRooms());
            bookingVO.setRoomNumbers(bookingInfoEntity.get().getRoomNumbers());
            bookingVO.setRoomPrice(bookingInfoEntity.get().getRoomPrice());
            bookingVO.setAadharNumber(bookingInfoEntity.get().getAadharNumber());
            bookingVO.setFromDate(outputFormat.format(bookingInfoEntity.get().getFromDate()));
            bookingVO.setToDate(outputFormat.format(bookingInfoEntity.get().getToDate()));
            bookingVO.setBookedOn(outputFormat.format(bookingInfoEntity.get().getBookedOn()));
            return bookingVO;
        }
        return null;
    }
    @Transactional
    public BookingVO updateTransaction(int transactionId, int bookingId) {

        Optional<BookingInfoEntity> optionalBookingInfoEntity = bookingDAO.findById(bookingId);
        if(optionalBookingInfoEntity.isPresent()){
            optionalBookingInfoEntity.get().setTransactionId(transactionId);
            BookingInfoEntity booking = optionalBookingInfoEntity.get();
            booking.setTransactionId(transactionId);
            BookingInfoEntity bookingInfoEntity = bookingDAO.save(booking);

            BookingVO  bookingVO= new BookingVO();
            bookingVO.setId(bookingInfoEntity.getBookingId());
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            bookingVO.setNumOfRooms(bookingInfoEntity.getNumOfRooms());
            bookingVO.setRoomNumbers(bookingInfoEntity.getRoomNumbers());
            bookingVO.setRoomPrice(bookingInfoEntity.getRoomPrice());
            bookingVO.setAadharNumber(bookingInfoEntity.getAadharNumber());
            bookingVO.setFromDate(outputFormat.format(bookingInfoEntity.getFromDate()));
            bookingVO.setToDate(outputFormat.format(bookingInfoEntity.getToDate()));
            bookingVO.setBookedOn(outputFormat.format(bookingInfoEntity.getBookedOn()));
            bookingVO.setTransactionId(transactionId);

            return bookingVO;
        }
    return null;
    }

    @Override
    public boolean isBookingExists(int id) {

        return bookingDAO.existsById(id);
    }

    @Override
    public BookingVO makeBooking(BookingVO bookingVO){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fromDate = dateFormat.parse(bookingVO.getFromDate());
            Date toDate = dateFormat.parse(bookingVO.getToDate());
            long days = getDifferenceDays(fromDate,toDate);
            int numOfRooms = bookingVO.getNumOfRooms();
            long totalPrice = 1000*numOfRooms * days;
            ArrayList<String> roomList = getRandomNumbers((int)numOfRooms);
            String roomNumbers = getRoomNumbers(roomList);

            BookingInfoEntity bookingInfoEntity = bookingDAO.save(new BookingInfoEntity(fromDate,toDate, bookingVO.getAadharNumber(), numOfRooms,roomNumbers,(int)totalPrice,0,new Date()));
            bookingVO.setId(bookingInfoEntity.getBookingId());
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = outputFormat.format(new Date());
            bookingVO.setBookedOn(formattedDate);
            bookingVO.setAadharNumber(bookingVO.getAadharNumber());
            bookingVO.setNumOfRooms(numOfRooms);
            bookingVO.setRoomNumbers(roomNumbers);
            bookingVO.setRoomPrice((int)totalPrice);
        }
        catch(Exception e){
            e.printStackTrace();
        }



        return bookingVO;
    }



    public static ArrayList<String> getRandomNumbers(int count){
        Random rand = new Random();
        int upperBound = 100;
        ArrayList<String>numberList = new ArrayList<String>();

        for (int i=0; i<count; i++){
            numberList.add(String.valueOf(rand.nextInt(upperBound)));
        }

        return numberList;
    }

    public static int getRoomPrices(int numOfRooms, int days){
        return 1000*numOfRooms*days;
    }

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static String getRoomNumbers(ArrayList<String> roomNumbersList){
        return String.join(", ", roomNumbersList);
    }


}
