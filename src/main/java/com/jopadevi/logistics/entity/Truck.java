package com.jopadevi.logistics.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "trucks")
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String vehicleNumber;

    private String vehicleModel;

    private String vehicleType;

    private String manufacturer;

    private Integer manufacturingYear;

    private String fuelType;

    private Double currentOdometer;

    private String status;

    public Truck() {
    }

    public Long getId() {
        return id;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getManufacturingYear() {
        return manufacturingYear;
    }

    public void setManufacturingYear(Integer manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public Double getCurrentOdometer() {
        return currentOdometer;
    }

    public void setCurrentOdometer(Double currentOdometer) {
        this.currentOdometer = currentOdometer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
 // ================================
 // MAINTENANCE HISTORY
 // ================================

    @JsonIgnore
    @OneToMany(
        mappedBy = "truck",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Maintenance> maintenanceRecords = new ArrayList<>();
 
 public List<Maintenance> getMaintenanceRecords() {
	    return maintenanceRecords;
	}

	public void setMaintenanceRecords(
	        List<Maintenance> maintenanceRecords
	) {
	    this.maintenanceRecords = maintenanceRecords;
	}
}