package com.jopadevi.logistics.controller;

import com.jopadevi.logistics.entity.Expense;
import com.jopadevi.logistics.entity.Trip;
import com.jopadevi.logistics.repository.ExpenseRepository;
import com.jopadevi.logistics.repository.TripRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:5173")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;
    private final TripRepository tripRepository;

    public ExpenseController(
            ExpenseRepository expenseRepository,
            TripRepository tripRepository
    ) {
        this.expenseRepository = expenseRepository;
        this.tripRepository = tripRepository;
    }


    // ==========================================
    // GET ALL EXPENSES
    // ==========================================

    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }


    // ==========================================
    // GET EXPENSE BY ID
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(
            @PathVariable Long id
    ) {

        return expenseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // ==========================================
    // GET EXPENSES OF A TRIP
    // ==========================================

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<List<Expense>> getExpensesByTrip(
            @PathVariable Long tripId
    ) {

        if (!tripRepository.existsById(tripId)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                expenseRepository.findByTripId(tripId)
        );
    }


    // ==========================================
    // ADD EXPENSE TO TRIP
    // ==========================================

    @PostMapping("/trip/{tripId}")
    public ResponseEntity<?> addExpense(
            @PathVariable Long tripId,
            @RequestBody Expense expense
    ) {

        Trip trip = tripRepository.findById(tripId)
                .orElse(null);

        if (trip == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        expense.setTrip(trip);

        Expense savedExpense =
                expenseRepository.save(expense);

        return ResponseEntity.ok(savedExpense);
    }


    // ==========================================
    // UPDATE EXPENSE
    // ==========================================

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(
            @PathVariable Long id,
            @RequestBody Expense updatedExpense
    ) {

        Expense existingExpense =
                expenseRepository.findById(id)
                        .orElse(null);

        if (existingExpense == null) {
            return ResponseEntity.notFound().build();
        }

        existingExpense.setTitle(
                updatedExpense.getTitle()
        );

        existingExpense.setCategory(
                updatedExpense.getCategory()
        );

        existingExpense.setAmount(
                updatedExpense.getAmount()
        );

        existingExpense.setExpenseDate(
                updatedExpense.getExpenseDate()
        );

        existingExpense.setDescription(
                updatedExpense.getDescription()
        );

        existingExpense.setVehicleNumber(
                updatedExpense.getVehicleNumber()
        );

        Expense savedExpense =
                expenseRepository.save(existingExpense);

        return ResponseEntity.ok(savedExpense);
    }


    // ==========================================
    // DELETE EXPENSE
    // ==========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(
            @PathVariable Long id
    ) {

        if (!expenseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        expenseRepository.deleteById(id);

        return ResponseEntity.ok(
                "Expense deleted successfully"
        );
    }
    
    
    
 // ==========================================
 // ADD EXPENSE WITHOUT TRIP
 // ==========================================

 @PostMapping
 public ResponseEntity<?> addExpense(
         @RequestBody Expense expense
 ) {

     Expense savedExpense =
             expenseRepository.save(expense);

     return ResponseEntity.ok(savedExpense);
 }
}