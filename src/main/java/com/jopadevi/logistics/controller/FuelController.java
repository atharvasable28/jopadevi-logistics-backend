package com.jopadevi.logistics.controller;

import com.jopadevi.logistics.entity.Fuel;
import com.jopadevi.logistics.entity.Truck;
import com.jopadevi.logistics.repository.FuelRepository;
import com.jopadevi.logistics.repository.TruckRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fuel")
@CrossOrigin(origins = "http://localhost:5173")
public class FuelController {

    private final FuelRepository fuelRepository;
    private final TruckRepository truckRepository;

    public FuelController(
            FuelRepository fuelRepository,
            TruckRepository truckRepository
    ) {
        this.fuelRepository = fuelRepository;
        this.truckRepository = truckRepository;
    }


    // ==========================================
    // GET ALL FUEL RECORDS
    // ==========================================

    @GetMapping
    public List<Fuel> getAllFuel() {

        return fuelRepository.findAll();
    }


    // ==========================================
    // GET FUEL BY ID
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<Fuel> getFuelById(
            @PathVariable Long id
    ) {

        return fuelRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // ==========================================
    // GET FUEL RECORDS OF A TRUCK
    // ==========================================

    @GetMapping("/truck/{truckId}")
    public ResponseEntity<List<Fuel>> getFuelByTruck(
            @PathVariable Long truckId
    ) {

        if (!truckRepository.existsById(truckId)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                fuelRepository.findByTruckId(truckId)
        );
    }


    // ==========================================
    // ADD FUEL TO TRUCK
    // ==========================================

    @PostMapping("/truck/{truckId}")
    public ResponseEntity<?> addFuel(
            @PathVariable Long truckId,
            @RequestBody Fuel fuel
    ) {

        Truck truck = truckRepository.findById(truckId)
                .orElse(null);

        if (truck == null) {
            return ResponseEntity.notFound().build();
        }

        /*
         * Automatically connect the fuel record
         * with the selected truck.
         */
        fuel.setTruck(truck);


        /*
         * Calculate total amount automatically
         * if quantity and price are provided.
         *
         * Example:
         * 100 litres × ₹95 = ₹9500
         */
        if (fuel.getQuantity() != null
                && fuel.getPricePerLiter() != null) {

            double total =
                    fuel.getQuantity()
                    * fuel.getPricePerLiter();

            fuel.setTotalAmount(total);
        }


        Fuel savedFuel =
                fuelRepository.save(fuel);

        return ResponseEntity.ok(savedFuel);
    }


    // ==========================================
    // UPDATE FUEL
    // ==========================================

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFuel(
            @PathVariable Long id,
            @RequestBody Fuel updatedFuel
    ) {

        Fuel existingFuel =
                fuelRepository.findById(id)
                        .orElse(null);

        if (existingFuel == null) {
            return ResponseEntity.notFound().build();
        }


        existingFuel.setFuelDate(
                updatedFuel.getFuelDate()
        );

        existingFuel.setFuelType(
                updatedFuel.getFuelType()
        );

        existingFuel.setQuantity(
                updatedFuel.getQuantity()
        );

        existingFuel.setPricePerLiter(
                updatedFuel.getPricePerLiter()
        );

        existingFuel.setOdometerReading(
                updatedFuel.getOdometerReading()
        );

        existingFuel.setFuelStation(
                updatedFuel.getFuelStation()
        );

        existingFuel.setNotes(
                updatedFuel.getNotes()
        );


        /*
         * Recalculate total amount
         */
        if (existingFuel.getQuantity() != null
                && existingFuel.getPricePerLiter() != null) {

            double total =
                    existingFuel.getQuantity()
                    * existingFuel.getPricePerLiter();

            existingFuel.setTotalAmount(total);
        }


        Fuel savedFuel =
                fuelRepository.save(existingFuel);

        return ResponseEntity.ok(savedFuel);
    }


    // ==========================================
    // DELETE FUEL
    // ==========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFuel(
            @PathVariable Long id
    ) {

        if (!fuelRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        fuelRepository.deleteById(id);

        return ResponseEntity.ok(
                "Fuel record deleted successfully"
        );
    }
}