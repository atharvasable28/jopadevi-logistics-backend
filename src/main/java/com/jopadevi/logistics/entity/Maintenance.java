package com.jopadevi.logistics.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "maintenance")
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ================================
    // TRUCK
    // ================================
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "truck_id")
    private Truck truck;


    // ================================
    // MAINTENANCE INFORMATION
    // ================================

    private String maintenanceType;

    private String serviceProvider;

    private LocalDate maintenanceDate;

    private Double cost;

    private Double odometerReading;


    // ================================
    // DESCRIPTION
    // ================================

    @Column(length = 1000)
    private String description;


    // ================================
    // STATUS
    // ================================

    private String status;


    // ================================
    // CONSTRUCTOR
    // ================================

    public Maintenance() {
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


    public String getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(String maintenanceType) {
        this.maintenanceType = maintenanceType;
    }


    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }


    public LocalDate getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(LocalDate maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }


    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }


    public Double getOdometerReading() {
        return odometerReading;
    }

    public void setOdometerReading(Double odometerReading) {
        this.odometerReading = odometerReading;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}