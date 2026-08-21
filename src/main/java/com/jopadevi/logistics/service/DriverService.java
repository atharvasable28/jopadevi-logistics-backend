package com.jopadevi.logistics.service;

import com.jopadevi.logistics.entity.Driver;
import com.jopadevi.logistics.entity.Truck;
import com.jopadevi.logistics.repository.DriverRepository;
import com.jopadevi.logistics.repository.TruckRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    private final TruckRepository truckRepository;


    public DriverService(
            DriverRepository driverRepository,
            TruckRepository truckRepository) {

        this.driverRepository =
                driverRepository;

        this.truckRepository =
                truckRepository;
    }


    /* ================================
       ADD DRIVER
    ================================= */

    public Driver addDriver(
            Driver driver,
            Long truckId) {

        if (
            driver.getLicenseNumber() != null &&
            driverRepository.existsByLicenseNumber(
                driver.getLicenseNumber()
            )
        ) {

            throw new RuntimeException(
                "License number already exists"
            );
        }


        if (truckId != null) {

            Truck truck =
                    truckRepository
                        .findById(truckId)
                        .orElseThrow(() ->
                            new RuntimeException(
                                "Truck not found"
                            )
                        );

            driver.setTruck(truck);
        }


        return driverRepository.save(driver);
    }


    /* ================================
       GET ALL
    ================================= */

    public List<Driver> getAllDrivers() {

        return driverRepository.findAll();

    }


    /* ================================
       GET BY ID
    ================================= */

    public Driver getDriverById(Long id) {

        return driverRepository
                .findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Driver not found"
                    )
                );
    }


    /* ================================
       UPDATE
    ================================= */

    public Driver updateDriver(
            Long id,
            Driver updatedDriver,
            Long truckId) {

        Driver existingDriver =
                getDriverById(id);


        existingDriver.setName(
                updatedDriver.getName()
        );


        existingDriver.setPhone(
                updatedDriver.getPhone()
        );


        existingDriver.setAlternatePhone(
                updatedDriver.getAlternatePhone()
        );


        existingDriver.setLicenseNumber(
                updatedDriver.getLicenseNumber()
        );


        existingDriver.setLicenseType(
                updatedDriver.getLicenseType()
        );


        existingDriver.setLicenseExpiry(
                updatedDriver.getLicenseExpiry()
        );


        existingDriver.setMonthlySalary(
                updatedDriver.getMonthlySalary()
        );


        existingDriver.setAdvance(
                updatedDriver.getAdvance()
        );


        existingDriver.setAddress(
                updatedDriver.getAddress()
        );


        existingDriver.setJoiningDate(
                updatedDriver.getJoiningDate()
        );


        existingDriver.setStatus(
                updatedDriver.getStatus()
        );


        if (truckId != null) {

            Truck truck =
                    truckRepository
                        .findById(truckId)
                        .orElseThrow(() ->
                            new RuntimeException(
                                "Truck not found"
                            )
                        );

            existingDriver.setTruck(truck);

        } else {

            existingDriver.setTruck(null);

        }


        return driverRepository.save(
                existingDriver
        );
    }


    /* ================================
       DELETE
    ================================= */

    public void deleteDriver(Long id) {

        if (
            !driverRepository.existsById(id)
        ) {

            throw new RuntimeException(
                "Driver not found"
            );

        }


        driverRepository.deleteById(id);

    }

}