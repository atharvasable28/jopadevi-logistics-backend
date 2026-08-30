package com.jopadevi.logistics.repository;

import com.jopadevi.logistics.entity.BillPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillPaymentRepository
        extends JpaRepository<BillPayment, Long> {

    List<BillPayment> findByBillId(Long billId);

    void deleteByBillId(Long billId);

}