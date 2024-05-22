package com.project.paymentservice.service;

import com.project.paymentservice.DAO.PaymentDAO;
import com.project.paymentservice.DAO.PaymentVO;
import com.project.paymentservice.model.TransactionDetailsEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class PaymentServiceImpl implements  PaymentService{

    @Autowired
    private PaymentDAO paymentDAO;

    @Override
    public int makePayment(PaymentVO paymentVO) {
        try{
            TransactionDetailsEntity transactionDetailsEntity = paymentDAO.save(new TransactionDetailsEntity(paymentVO.getPaymentMode(),
                    paymentVO.getBookingId(), paymentVO.getCardNumber(), paymentVO.getUpiId()));
            paymentVO.setTransactionId(transactionDetailsEntity.getTransactionId());
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return paymentVO.getTransactionId();
    }

    @Override
    public Optional<TransactionDetailsEntity> findTransactionById(int transactionId) {
        Optional<TransactionDetailsEntity> transactionDetailsEntity = paymentDAO.findById(transactionId);
        if(transactionDetailsEntity.isPresent()){
            return transactionDetailsEntity;
        }
        return null;
    }
}
