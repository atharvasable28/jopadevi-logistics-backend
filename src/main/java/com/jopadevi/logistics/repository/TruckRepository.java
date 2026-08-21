package com.jopadevi.logistics.repository;

import com.jopadevi.logistics.entity.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TruckRepository extends JpaRepository<Truck, Long> {

    boolean existsByVehicleNumber(String vehicleNumber);

}