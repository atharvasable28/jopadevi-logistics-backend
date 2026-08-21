package com.jopadevi.logistics.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "fuel")
public class Fuel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // ================================
    // TRUCK
    // ================================

    @ManyToOne
    @JoinColumn(name = "truck_id")
    private Truck truck;


    // ================================
    // FUEL INFORMATION
    // ================================

    private LocalDate fuelDate;

    private String fuelType;

    private Double quantity;

    private Double pricePerLiter;

    private Double totalAmount;

    private Double odometerReading;


    // ================================
    // FUEL STATION
    // ================================

    private String fuelStation;


    // ================================
    // NOTES
    // ================================

    @Column(length = 1000)
    private String notes;


    // ================================
    // CONSTRUCTOR
    // ================================

    public Fuel() {
    }


    // ================================
    // GETTERS / SETTERS
    // ================================

    public Long getId() {
        return id;
    }


    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }


    public LocalDate getFuelDate() {
        return fuelDate;
    }

    public void setFuelDate(LocalDate fuelDate) {
        this.fuelDate = fuelDate;
    }


    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }


    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }


    public Double getPricePerLiter() {
        return pricePerLiter;
    }

    public void setPricePerLiter(Double pricePerLiter) {
        this.pricePerLiter = pricePerLiter;
    }


    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }


    public Double getOdometerReading() {
        return odometerReading;
    }

    public void setOdometerReading(Double odometerReading) {
        this.odometerReading = odometerReading;
    }


    public String getFuelStation() {
        return fuelStation;
    }

    public void setFuelStation(String fuelStation) {
        this.fuelStation = fuelStation;
    }


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}