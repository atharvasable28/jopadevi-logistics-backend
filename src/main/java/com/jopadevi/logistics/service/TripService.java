package com.jopadevi.logistics.service;

import com.jopadevi.logistics.entity.Driver;
import com.jopadevi.logistics.entity.Trip;
import com.jopadevi.logistics.entity.Truck;
import com.jopadevi.logistics.repository.DriverRepository;
import com.jopadevi.logistics.repository.TripRepository;
import com.jopadevi.logistics.repository.TruckRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {

    private final TripRepository tripRepository;

    private final TruckRepository truckRepository;

    private final DriverRepository driverRepository;


    public TripService(
            TripRepository tripRepository,
            TruckRepository truckRepository,
            DriverRepository driverRepository) {

        this.tripRepository = tripRepository;
        this.truckRepository = truckRepository;
        this.driverRepository = driverRepository;
    }


    /* =========================================
       GET ALL TRIPS
    ========================================= */

    public List<Trip> getAllTrips() {

        return tripRepository.findAll();

    }


    /* =========================================
       GET TRIP BY ID
    ========================================= */

    public Trip getTripById(Long id) {

        return tripRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Trip not found"
                        )
                );

    }


    /* =========================================
       CREATE TRIP
    ========================================= */

    public Trip createTrip(
            Long truckId,
            Long driverId,
            Trip trip) {


        /* -----------------------------
           FIND TRUCK
        ----------------------------- */

        Truck truck =
                truckRepository
                        .findById(truckId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Truck not found"
                                )
                        );


        /* -----------------------------
           FIND DRIVER
        ----------------------------- */

        Driver driver =
                driverRepository
                        .findById(driverId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Driver not found"
                                )
                        );


        /* -----------------------------
           ASSIGN
        ----------------------------- */

        trip.setTruck(truck);

        trip.setDriver(driver);


        /* -----------------------------
           DEFAULT STATUS
        ----------------------------- */

        if (
                trip.getStatus() == null ||
                trip.getStatus().isBlank()
        ) {

            trip.setStatus("PLANNED");

        }


        /* -----------------------------
           SAVE
        ----------------------------- */

        return tripRepository.save(trip);

    }


    /* =========================================
       UPDATE TRIP
    ========================================= */

    public Trip updateTrip(
            Long id,
            Long truckId,
            Long driverId,
            Trip updatedTrip) {


        Trip existingTrip =
                tripRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Trip not found"
                                )
                        );


        /* -----------------------------
           TRUCK
        ----------------------------- */

        if (truckId != null) {

            Truck truck =
                    truckRepository
                            .findById(truckId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Truck not found"
                                    )
                            );

            existingTrip.setTruck(truck);

        }


        /* -----------------------------
           DRIVER
        ----------------------------- */

        if (driverId != null) {

            Driver driver =
                    driverRepository
                            .findById(driverId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Driver not found"
                                    )
                            );

            existingTrip.setDriver(driver);

        }


        /* -----------------------------
           UPDATE FIELDS
        ----------------------------- */

        existingTrip.setSource(
                updatedTrip.getSource()
        );

        existingTrip.setDestination(
                updatedTrip.getDestination()
        );

        existingTrip.setTripDate(
                updatedTrip.getTripDate()
        );

        existingTrip.setReturnDate(
                updatedTrip.getReturnDate()
        );

        existingTrip.setCompanyName(
                updatedTrip.getCompanyName()
        );

        existingTrip.setBillAmount(
                updatedTrip.getBillAmount()
        );

        existingTrip.setStatus(
                updatedTrip.getStatus()
        );

        existingTrip.setNotes(
                updatedTrip.getNotes()
        );


        return tripRepository.save(
                existingTrip
        );

    }


    /* =========================================
       DELETE TRIP
    ========================================= */

    public void deleteTrip(Long id) {

        if (
                !tripRepository.existsById(id)
        ) {

            throw new RuntimeException(
                    "Trip not found"
            );

        }


        tripRepository.deleteById(id);

    }

}