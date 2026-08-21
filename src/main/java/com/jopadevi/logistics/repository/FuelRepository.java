package com.jopadevi.logistics.repository;

import com.jopadevi.logistics.entity.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuelRepository extends JpaRepository<Fuel, Long> {

    // Get all fuel records for a particular truck
    List<Fuel> findByTruckId(Long truckId);

    // Delete all fuel records belonging to a truck
    void deleteByTruckId(Long truckId);
}