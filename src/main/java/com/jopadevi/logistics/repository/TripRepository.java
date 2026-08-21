package com.jopadevi.logistics.repository;

import com.jopadevi.logistics.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository
        extends JpaRepository<Trip, Long> {
}