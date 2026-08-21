package com.jopadevi.logistics.repository;

import com.jopadevi.logistics.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Get all expenses for a particular trip
    List<Expense> findByTripId(Long tripId);

    // Delete all expenses belonging to a trip
    void deleteByTripId(Long tripId);
}