package com.jopadevi.logistics.repository;

import com.jopadevi.logistics.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository
        extends JpaRepository<Document, Long> {

    // Get all documents for a truck
    List<Document> findByTruckId(Long truckId);

    // Delete all documents belonging to a truck
    void deleteByTruckId(Long truckId);
}