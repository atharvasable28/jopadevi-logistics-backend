package com.jopadevi.logistics.repository;

import com.jopadevi.logistics.entity.SalaryPayment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryPaymentRepository
        extends JpaRepository<SalaryPayment, Long> {

    List<SalaryPayment>
    findByDriverId(Long driverId);

}