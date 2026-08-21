package com.jopadevi.logistics.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "documents")
public class Document {

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
    // DOCUMENT INFORMATION
    // ================================

    private String documentType;

    private String documentNumber;

    private LocalDate issueDate;

    private LocalDate expiryDate;

    private String status;


    // ================================
    // DOCUMENT FILE
    // ================================

    private String fileName;

    private String fileUrl;


    // ================================
    // NOTES
    // ================================

    @Column(length = 1000)
    private String notes;


    // ================================
    // CONSTRUCTOR
    // ================================

    public Document() {
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


    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }


    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }


    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }


    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}