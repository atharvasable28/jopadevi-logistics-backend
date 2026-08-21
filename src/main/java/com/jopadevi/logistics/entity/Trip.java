package com.jopadevi.logistics.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
//import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /* ================================
       TRUCK
    ================================= */

    @ManyToOne
    @JoinColumn(name = "truck_id")
    private Truck truck;


    /* ================================
       DRIVER
    ================================= */

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;


    /* ================================
       EXPENSES
    ================================= */

    @OneToMany(
    	    mappedBy = "trip",
    	    cascade = CascadeType.ALL,
    	    orphanRemoval = true
    	)
//    	@JsonManagedReference
    	private List<Expense> expenses = new ArrayList<>();


    /* ================================
       TRIP INFORMATION
    ================================= */

    private String source;

    private String destination;

    private String tripDate;

    private String returnDate;

    private String companyName;


    /* ================================
       BILL INFORMATION
    ================================= */

    private Double billAmount;


    /* ================================
       STATUS
    ================================= */

    private String status;


    /* ================================
       NOTES
    ================================= */

    @Column(length = 1000)
    private String notes;


    /* ================================
       CONSTRUCTOR
    ================================= */

    public Trip() {
    }


    /* ================================
       GETTERS / SETTERS
    ================================= */

    public Long getId() {
        return id;
    }


    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }


    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }


    /* ================================
       EXPENSE GETTER / SETTER
    ================================= */

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }


    /* ================================
       TRIP GETTERS / SETTERS
    ================================= */

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }


    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }


    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public Double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(Double billAmount) {
        this.billAmount = billAmount;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}