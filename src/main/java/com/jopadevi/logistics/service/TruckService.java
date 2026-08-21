package com.jopadevi.logistics.service;

import com.jopadevi.logistics.entity.Truck;
import com.jopadevi.logistics.repository.TruckRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TruckService {

    private final TruckRepository truckRepository;

    public TruckService(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }


    public Truck addTruck(Truck truck) {

        if (truckRepository.existsByVehicleNumber(
                truck.getVehicleNumber())) {

            throw new RuntimeException(
                    "Vehicle number already exists"
            );
        }

        return truckRepository.save(truck);
    }


    public List<Truck> getAllTrucks() {

        return truckRepository.findAll();

    }


    public Truck getTruckById(Long id) {

        return truckRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Truck not found"
                        )
                );
    }


    public Truck updateTruck(
            Long id,
            Truck updatedTruck) {

        Truck existingTruck =
                getTruckById(id);

        existingTruck.setVehicleNumber(
                updatedTruck.getVehicleNumber()
        );

        existingTruck.setVehicleModel(
                updatedTruck.getVehicleModel()
        );

        existingTruck.setVehicleType(
                updatedTruck.getVehicleType()
        );

        existingTruck.setManufacturer(
                updatedTruck.getManufacturer()
        );

        existingTruck.setManufacturingYear(
                updatedTruck.getManufacturingYear()
        );

        existingTruck.setFuelType(
                updatedTruck.getFuelType()
        );

        existingTruck.setCurrentOdometer(
                updatedTruck.getCurrentOdometer()
        );

        existingTruck.setStatus(
                updatedTruck.getStatus()
        );

        return truckRepository.save(existingTruck);
    }


    public void deleteTruck(Long id) {

        if (!truckRepository.existsById(id)) {

            throw new RuntimeException(
                    "Truck not found"
            );

        }

        truckRepository.deleteById(id);

    }

}