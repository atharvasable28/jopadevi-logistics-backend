package com.jopadevi.logistics.controller;

import com.jopadevi.logistics.entity.Truck;
import com.jopadevi.logistics.service.TruckService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trucks")
//@CrossOrigin(origins = "http://localhost:5173")
public class TruckController {

    private final TruckService truckService;

    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }


    @PostMapping
    public ResponseEntity<Truck> addTruck(
            @RequestBody Truck truck) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(truckService.addTruck(truck));
    }


    @GetMapping
    public ResponseEntity<List<Truck>> getAllTrucks() {

        return ResponseEntity.ok(
                truckService.getAllTrucks()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<Truck> getTruck(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                truckService.getTruckById(id)
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<Truck> updateTruck(
            @PathVariable Long id,
            @RequestBody Truck truck) {

        return ResponseEntity.ok(
                truckService.updateTruck(id, truck)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTruck(
            @PathVariable Long id) {

        truckService.deleteTruck(id);

        return ResponseEntity.ok(
                "Truck deleted successfully"
        );
    }

}