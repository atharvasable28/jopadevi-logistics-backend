package com.jopadevi.logistics.repository;

import com.jopadevi.logistics.entity.EMIPayment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EMIPaymentRepository
        extends JpaRepository<EMIPayment, Long> {

    List<EMIPayment> findByVehicleEMIId(Long emiId);

    boolean existsByVehicleEMIIdAndEmiNumber(
            Long emiId,
            Integer emiNumber
    );

}