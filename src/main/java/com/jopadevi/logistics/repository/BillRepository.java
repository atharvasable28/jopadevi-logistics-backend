package com.jopadevi.logistics.repository;

import com.jopadevi.logistics.entity.Bill;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {

    boolean existsByBillNumber(String billNumber);

}