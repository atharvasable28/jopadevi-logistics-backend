package com.jopadevi.logistics.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "vehicle_emi")
public class VehicleEMI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /* ================================
       VEHICLE
    ================================= */

    @OneToOne
    @JoinColumn(name = "truck_id", nullable = false, unique = true)
    private Truck truck;


    /* ================================
       LOAN INFORMATION
    ================================= */

    private String lenderName;

    private String loanAccountNumber;


    @Column(precision = 12, scale = 2)
    private BigDecimal loanAmount;


    @Column(precision = 12, scale = 2)
    private BigDecimal monthlyEMI;


    private Integer totalEMIs;

    private Integer paidEMIs;

    private LocalDate loanStartDate;

    private LocalDate loanEndDate;


    /* ================================
       STATUS
    ================================= */

    private String status;


    /* ================================
       CONSTRUCTOR
    ================================= */

    public VehicleEMI() {
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


    public String getLenderName() {
        return lenderName;
    }

    public void setLenderName(String lenderName) {
        this.lenderName = lenderName;
    }


    public String getLoanAccountNumber() {
        return loanAccountNumber;
    }

    public void setLoanAccountNumber(String loanAccountNumber) {
        this.loanAccountNumber = loanAccountNumber;
    }


    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }


    public BigDecimal getMonthlyEMI() {
        return monthlyEMI;
    }

    public void setMonthlyEMI(BigDecimal monthlyEMI) {
        this.monthlyEMI = monthlyEMI;
    }


    public Integer getTotalEMIs() {
        return totalEMIs;
    }

    public void setTotalEMIs(Integer totalEMIs) {
        this.totalEMIs = totalEMIs;
    }


    public Integer getPaidEMIs() {
        return paidEMIs;
    }

    public void setPaidEMIs(Integer paidEMIs) {
        this.paidEMIs = paidEMIs;
    }


    public LocalDate getLoanStartDate() {
        return loanStartDate;
    }

    public void setLoanStartDate(LocalDate loanStartDate) {
        this.loanStartDate = loanStartDate;
    }


    public LocalDate getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(LocalDate loanEndDate) {
        this.loanEndDate = loanEndDate;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}