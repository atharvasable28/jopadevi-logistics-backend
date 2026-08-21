package com.jopadevi.logistics.controller;

import com.jopadevi.logistics.entity.Bill;
import com.jopadevi.logistics.service.BillService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
@CrossOrigin(origins = "http://localhost:5173")
public class BillController {

    private final BillService billService;


    public BillController(
            BillService billService) {

        this.billService = billService;
    }


    /* ================================
       ADD BILL
    ================================= */

    @PostMapping
    public ResponseEntity<Bill> addBill(

            @RequestParam Long companyId,

            @RequestParam(required = false)
            Long tripId,

            @RequestBody Bill bill) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        billService.addBill(
                                bill,
                                companyId,
                                tripId
                        )
                );
    }


    /* ================================
       GET ALL BILLS
    ================================= */

    @GetMapping
    public ResponseEntity<List<Bill>>
    getAllBills() {

        return ResponseEntity.ok(
                billService.getAllBills()
        );
    }


    /* ================================
       GET BILL BY ID
    ================================= */

    @GetMapping("/{id}")
    public ResponseEntity<Bill>
    getBillById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                billService.getBillById(id)
        );
    }


    /* ================================
       UPDATE BILL
    ================================= */

    @PutMapping("/{id}")
    public ResponseEntity<Bill>
    updateBill(
            @PathVariable Long id,
            @RequestBody Bill bill) {

        return ResponseEntity.ok(
                billService.updateBill(
                        id,
                        bill
                )
        );
    }


    /* ================================
       DELETE BILL
    ================================= */

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deleteBill(
            @PathVariable Long id) {

        billService.deleteBill(id);

        return ResponseEntity.ok(
                "Bill deleted successfully"
        );
    }

}