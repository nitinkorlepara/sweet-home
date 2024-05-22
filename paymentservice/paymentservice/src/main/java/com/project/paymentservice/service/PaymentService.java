package com.project.paymentservice.service;

import com.project.paymentservice.DAO.PaymentVO;
import com.project.paymentservice.model.TransactionDetailsEntity;

import java.util.Optional;

public interface PaymentService {

    public int makePayment(PaymentVO paymentVO);

    public Optional<TransactionDetailsEntity> findTransactionById(int transactionId);
}
