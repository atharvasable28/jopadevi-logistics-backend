package com.jopadevi.logistics.repository;

import com.jopadevi.logistics.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceRepository
        extends JpaRepository<Maintenance, Long> {

    // Get all maintenance records for a truck
    List<Maintenance> findByTruckId(Long truckId);

    // Delete all maintenance records of a truck
    void deleteByTruckId(Long truckId);
}