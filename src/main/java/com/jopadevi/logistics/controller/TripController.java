package com.jopadevi.logistics.controller;

import com.jopadevi.logistics.entity.Trip;
import com.jopadevi.logistics.service.TripService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "http://localhost:5173")
public class TripController {

    private final TripService tripService;


    public TripController(
            TripService tripService) {

        this.tripService = tripService;

    }


    /* =========================================
       GET ALL TRIPS
    ========================================= */

    @GetMapping
    public ResponseEntity<List<Trip>>
    getAllTrips() {

        return ResponseEntity.ok(
                tripService.getAllTrips()
        );

    }


    /* =========================================
       GET TRIP BY ID
    ========================================= */

    @GetMapping("/{id}")
    public ResponseEntity<Trip>
    getTripById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                tripService.getTripById(id)
        );

    }


    /* =========================================
       CREATE TRIP
    ========================================= */

    @PostMapping
    public ResponseEntity<Trip>
    createTrip(

            @RequestParam Long truckId,

            @RequestParam Long driverId,

            @RequestBody Trip trip

    ) {

        Trip savedTrip =
                tripService.createTrip(
                        truckId,
                        driverId,
                        trip
                );


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedTrip);

    }


    /* =========================================
       UPDATE TRIP
    ========================================= */

    @PutMapping("/{id}")
    public ResponseEntity<Trip>
    updateTrip(

            @PathVariable Long id,

            @RequestParam(required = false)
            Long truckId,

            @RequestParam(required = false)
            Long driverId,

            @RequestBody Trip trip

    ) {

        Trip updatedTrip =
                tripService.updateTrip(
                        id,
                        truckId,
                        driverId,
                        trip
                );


        return ResponseEntity.ok(
                updatedTrip
        );

    }


    /* =========================================
       DELETE TRIP
    ========================================= */

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deleteTrip(
            @PathVariable Long id) {

        tripService.deleteTrip(id);


        return ResponseEntity.ok(
                "Trip deleted successfully"
        );

    }

}