package com.jopadevi.logistics.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "emi_payments")
public class EMIPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /* ================================
       VEHICLE EMI
    ================================= */

    @ManyToOne
    @JoinColumn(name = "emi_id", nullable = false)
    private VehicleEMI vehicleEMI;


    /* ================================
       EMI INFORMATION
    ================================= */

    @Column(nullable = false)
    private Integer emiNumber;


    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;


    private LocalDate paymentDate;


    /* ================================
       PAYMENT INFORMATION
    ================================= */

    private String paymentMode;

    private String referenceNumber;


    @Column(length = 500)
    private String notes;


    /* ================================
       CONSTRUCTOR
    ================================= */

    public EMIPayment() {
    }


    /* ================================
       GETTERS / SETTERS
    ================================= */

    public Long getId() {
        return id;
    }


    public VehicleEMI getVehicleEMI() {
        return vehicleEMI;
    }

    public void setVehicleEMI(VehicleEMI vehicleEMI) {
        this.vehicleEMI = vehicleEMI;
    }


    public Integer getEmiNumber() {
        return emiNumber;
    }

    public void setEmiNumber(Integer emiNumber) {
        this.emiNumber = emiNumber;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }


    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }


    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}