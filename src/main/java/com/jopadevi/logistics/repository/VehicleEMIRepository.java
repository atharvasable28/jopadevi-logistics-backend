package com.jopadevi.logistics.repository;

import com.jopadevi.logistics.entity.VehicleEMI;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleEMIRepository
        extends JpaRepository<VehicleEMI, Long> {

    boolean existsByTruckId(Long truckId);

}