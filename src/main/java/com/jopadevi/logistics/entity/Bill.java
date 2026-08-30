package com.jopadevi.logistics.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ================================
       BILL NUMBER
    ================================= */

    @Column(nullable = false, unique = true)
    private String billNumber;

    /* ================================
       COMPANY
    ================================= */

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    /* ================================
       TRIP
    ================================= */

    @ManyToOne
    @JoinColumn(name = "trip_id")
    @JsonIgnore
    private Trip trip;
    
    
    /* ================================
    BILL PAYMENTS
 ================================= */

 @OneToMany(
         mappedBy = "bill",
         cascade = CascadeType.ALL,
         orphanRemoval = true
 )
 @JsonIgnore
 private List<BillPayment> payments = new ArrayList<>();

    /* ================================
       BILL INFORMATION
    ================================= */

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal paidAmount = BigDecimal.ZERO;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal remainingAmount = BigDecimal.ZERO;

    private LocalDate billDate;

    private LocalDate dueDate;

    @Column(length = 1000)
    private String description;

    /* ================================
       STATUS
    ================================= */

    private String status;

    /* ================================
       CONSTRUCTOR
    ================================= */

    public Bill() {
    }

    /* ================================
       GETTERS / SETTERS
    ================================= */

    public Long getId() {
        return id;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
    
    

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
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
    
    public List<BillPayment> getPayments() {
        return payments;
    }

    public void setPayments(List<BillPayment> payments) {
        this.payments = payments;
    }
}