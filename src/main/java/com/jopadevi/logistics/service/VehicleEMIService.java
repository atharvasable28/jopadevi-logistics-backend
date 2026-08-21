package com.jopadevi.logistics.service;

import com.jopadevi.logistics.entity.Truck;
import com.jopadevi.logistics.entity.VehicleEMI;

import com.jopadevi.logistics.repository.TruckRepository;
import com.jopadevi.logistics.repository.VehicleEMIRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleEMIService {

    private final VehicleEMIRepository emiRepository;

    private final TruckRepository truckRepository;


    public VehicleEMIService(
            VehicleEMIRepository emiRepository,
            TruckRepository truckRepository) {

        this.emiRepository = emiRepository;
        this.truckRepository = truckRepository;
    }


    /* ================================
       ADD EMI
    ================================= */

    public VehicleEMI addEMI(
            Long truckId,
            VehicleEMI emi) {


        /* Check truck */

        Truck truck =
                truckRepository.findById(truckId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Truck not found"
                                )
                        );


        /* Check existing EMI */

        if (emiRepository.existsByTruckId(truckId)) {

            throw new RuntimeException(
                    "This truck already has an EMI"
            );
        }


        emi.setTruck(truck);


        /* Default paid EMI */

        if (emi.getPaidEMIs() == null) {

            emi.setPaidEMIs(0);

        }


        /* Default status */

        if (emi.getStatus() == null ||
                emi.getStatus().isBlank()) {

            emi.setStatus("ACTIVE");

        }


        return emiRepository.save(emi);
    }


    /* ================================
       GET ALL EMI
    ================================= */

    public List<VehicleEMI> getAllEMIs() {

        return emiRepository.findAll();

    }


    /* ================================
       GET EMI BY ID
    ================================= */

    public VehicleEMI getEMIById(Long id) {

        return emiRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "EMI record not found"
                        )
                );

    }


    /* ================================
       GET EMI BY TRUCK
    ================================= */

    public VehicleEMI getEMIByTruck(
            Long truckId) {

        return emiRepository.findAll()
                .stream()
                .filter(emi ->
                        emi.getTruck()
                                .getId()
                                .equals(truckId)
                )
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "No EMI found for this truck"
                        )
                );

    }


    /* ================================
       DELETE EMI
    ================================= */

    public void deleteEMI(Long id) {

        if (!emiRepository.existsById(id)) {

            throw new RuntimeException(
                    "EMI record not found"
            );

        }

        emiRepository.deleteById(id);

    }

}