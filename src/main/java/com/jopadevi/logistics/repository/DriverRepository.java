package com.jopadevi.logistics.repository;

import com.jopadevi.logistics.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository
        extends JpaRepository<Driver, Long> {

    boolean existsByLicenseNumber(
            String licenseNumber
    );

}