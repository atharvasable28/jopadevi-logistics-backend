package com.jopadevi.logistics.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "salary_payments")
public class SalaryPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    private Double amount;

    private Double advanceAdjusted;

    private String paymentDate;

    private String paymentMonth;

    private String paymentMode;

    private String notes;


    public SalaryPayment() {
    }


    public Long getId() {
        return id;
    }


    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


    public Double getAdvanceAdjusted() {
        return advanceAdjusted;
    }

    public void setAdvanceAdjusted(
            Double advanceAdjusted) {

        this.advanceAdjusted =
                advanceAdjusted;
    }


    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(
            String paymentDate) {

        this.paymentDate =
                paymentDate;
    }


    public String getPaymentMonth() {
        return paymentMonth;
    }

    public void setPaymentMonth(
            String paymentMonth) {

        this.paymentMonth =
                paymentMonth;
    }


    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(
            String paymentMode) {

        this.paymentMode =
                paymentMode;
    }


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {

        this.notes = notes;

    }

}