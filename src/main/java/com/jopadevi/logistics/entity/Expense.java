package com.jopadevi.logistics.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /* ================================
       EXPENSE TITLE
    ================================= */

    private String title;


    /* ================================
       CATEGORY
    ================================= */

    private String category;


    /* ================================
       AMOUNT
    ================================= */

    private Double amount;


    /* ================================
       EXPENSE DATE
    ================================= */

    private LocalDate expenseDate;


    /* ================================
       DESCRIPTION
    ================================= */

    @Column(length = 1000)
    private String description;


    /* ================================
       VEHICLE NUMBER
    ================================= */

    private String vehicleNumber;


    /* ================================
       TRIP
    ================================= */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "trip_id")
    @JsonBackReference
    private Trip trip;

    /* ================================
       CONSTRUCTOR
    ================================= */

    public Expense() {
    }


    /* ================================
       GETTERS / SETTERS
    ================================= */

    public Long getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }


    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

}