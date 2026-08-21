package com.jopadevi.logistics.controller;

import com.jopadevi.logistics.entity.Driver;
import com.jopadevi.logistics.service.DriverService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {


    private final DriverService driverService;


    public DriverController(
            DriverService driverService) {

        this.driverService =
                driverService;
    }


    /* ================================
       ADD DRIVER
    ================================= */

    @PostMapping
    public ResponseEntity<Driver> addDriver(

            @RequestBody Driver driver,

            @RequestParam(
                required = false
            )
            Long truckId

    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                    driverService.addDriver(
                        driver,
                        truckId
                    )
                );
    }


    /* ================================
       GET ALL
    ================================= */

    @GetMapping
    public ResponseEntity<List<Driver>>
    getAllDrivers() {

        return ResponseEntity.ok(
            driverService.getAllDrivers()
        );
    }


    /* ================================
       GET BY ID
    ================================= */

    @GetMapping("/{id}")
    public ResponseEntity<Driver>
    getDriver(
            @PathVariable Long id) {

        return ResponseEntity.ok(
            driverService.getDriverById(id)
        );
    }


    /* ================================
       UPDATE
    ================================= */

    @PutMapping("/{id}")
    public ResponseEntity<Driver>
    updateDriver(

            @PathVariable Long id,

            @RequestBody Driver driver,

            @RequestParam(
                required = false
            )
            Long truckId

    ) {

        return ResponseEntity.ok(
            driverService.updateDriver(
                id,
                driver,
                truckId
            )
        );
    }


    /* ================================
       DELETE
    ================================= */

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deleteDriver(
            @PathVariable Long id) {

        driverService.deleteDriver(id);

        return ResponseEntity.ok(
            "Driver deleted successfully"
        );
    }

}