package com.project.paymentservice.DAO;

import com.project.paymentservice.model.TransactionDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDAO extends JpaRepository<TransactionDetailsEntity,Integer> {

}
