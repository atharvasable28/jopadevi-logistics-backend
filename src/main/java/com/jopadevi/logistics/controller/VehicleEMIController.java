package com.jopadevi.logistics.controller;

import com.jopadevi.logistics.entity.VehicleEMI;
import com.jopadevi.logistics.service.VehicleEMIService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emi")
@CrossOrigin(origins = "http://localhost:5173")
public class VehicleEMIController {

    private final VehicleEMIService emiService;


    public VehicleEMIController(
            VehicleEMIService emiService) {

        this.emiService = emiService;

    }


    /* ================================
       ADD EMI
    ================================= */

    @PostMapping
    public ResponseEntity<VehicleEMI> addEMI(

            @RequestParam Long truckId,

            @RequestBody VehicleEMI emi) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        emiService.addEMI(
                                truckId,
                                emi
                        )
                );
    }


    /* ================================
       GET ALL EMI
    ================================= */

    @GetMapping
    public ResponseEntity<List<VehicleEMI>>
    getAllEMIs() {

        return ResponseEntity.ok(
                emiService.getAllEMIs()
        );

    }


    /* ================================
       GET EMI BY ID
    ================================= */

    @GetMapping("/{id}")
    public ResponseEntity<VehicleEMI>
    getEMIById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                emiService.getEMIById(id)
        );

    }


    /* ================================
       GET EMI BY TRUCK
    ================================= */

    @GetMapping("/truck/{truckId}")
    public ResponseEntity<VehicleEMI>
    getEMIByTruck(
            @PathVariable Long truckId) {

        return ResponseEntity.ok(
                emiService.getEMIByTruck(
                        truckId
                )
        );

    }


    /* ================================
       DELETE EMI
    ================================= */

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deleteEMI(
            @PathVariable Long id) {

        emiService.deleteEMI(id);

        return ResponseEntity.ok(
                "EMI record deleted successfully"
        );

    }

}