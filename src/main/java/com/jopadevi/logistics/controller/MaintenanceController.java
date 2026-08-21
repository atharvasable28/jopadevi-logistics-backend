package com.jopadevi.logistics.controller;

import com.jopadevi.logistics.entity.Maintenance;
import com.jopadevi.logistics.entity.Truck;
import com.jopadevi.logistics.repository.MaintenanceRepository;
import com.jopadevi.logistics.repository.TruckRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
@CrossOrigin(origins = "http://localhost:5173")
public class MaintenanceController {

    private final MaintenanceRepository maintenanceRepository;
    private final TruckRepository truckRepository;

    public MaintenanceController(
            MaintenanceRepository maintenanceRepository,
            TruckRepository truckRepository
    ) {
        this.maintenanceRepository = maintenanceRepository;
        this.truckRepository = truckRepository;
    }


    // ==========================================
    // GET ALL MAINTENANCE RECORDS
    // ==========================================

    @GetMapping
    public List<Maintenance> getAllMaintenance() {

        return maintenanceRepository.findAll();
    }


    // ==========================================
    // GET MAINTENANCE BY ID
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<Maintenance> getMaintenanceById(
            @PathVariable Long id
    ) {

        return maintenanceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // ==========================================
    // GET MAINTENANCE OF A TRUCK
    // ==========================================

    @GetMapping("/truck/{truckId}")
    public ResponseEntity<List<Maintenance>> getMaintenanceByTruck(
            @PathVariable Long truckId
    ) {

        if (!truckRepository.existsById(truckId)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                maintenanceRepository.findByTruckId(truckId)
        );
    }


    // ==========================================
    // ADD MAINTENANCE TO TRUCK
    // ==========================================

    @PostMapping("/truck/{truckId}")
    public ResponseEntity<?> addMaintenance(
            @PathVariable Long truckId,
            @RequestBody Maintenance maintenance
    ) {

        Truck truck = truckRepository.findById(truckId)
                .orElse(null);

        if (truck == null) {
            return ResponseEntity.notFound().build();
        }

        maintenance.setTruck(truck);

        Maintenance savedMaintenance =
                maintenanceRepository.save(maintenance);

        return ResponseEntity.ok(savedMaintenance);
    }


    // ==========================================
    // UPDATE MAINTENANCE
    // ==========================================

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMaintenance(
            @PathVariable Long id,
            @RequestBody Maintenance updatedMaintenance
    ) {

        Maintenance existingMaintenance =
                maintenanceRepository.findById(id)
                        .orElse(null);

        if (existingMaintenance == null) {
            return ResponseEntity.notFound().build();
        }


        existingMaintenance.setMaintenanceType(
                updatedMaintenance.getMaintenanceType()
        );

        existingMaintenance.setServiceProvider(
                updatedMaintenance.getServiceProvider()
        );

        existingMaintenance.setMaintenanceDate(
                updatedMaintenance.getMaintenanceDate()
        );

        existingMaintenance.setCost(
                updatedMaintenance.getCost()
        );

        existingMaintenance.setOdometerReading(
                updatedMaintenance.getOdometerReading()
        );

        existingMaintenance.setDescription(
                updatedMaintenance.getDescription()
        );

        existingMaintenance.setStatus(
                updatedMaintenance.getStatus()
        );


        Maintenance savedMaintenance =
                maintenanceRepository.save(existingMaintenance);

        return ResponseEntity.ok(savedMaintenance);
    }


    // ==========================================
    // DELETE MAINTENANCE
    // ==========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMaintenance(
            @PathVariable Long id
    ) {

        if (!maintenanceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        maintenanceRepository.deleteById(id);

        return ResponseEntity.ok(
                "Maintenance record deleted successfully"
        );
    }
}