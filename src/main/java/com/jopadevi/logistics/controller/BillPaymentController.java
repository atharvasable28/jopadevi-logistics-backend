package com.jopadevi.logistics.controller;

import com.jopadevi.logistics.entity.BillPayment;
import com.jopadevi.logistics.service.BillService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bill-payments")
@CrossOrigin(origins = "http://localhost:5173")
public class BillPaymentController {

    private final BillService billService;

    public BillPaymentController(
            BillService billService) {

        this.billService = billService;
    }

    /* ================================
       RECEIVE PAYMENT
    ================================= */

    @PostMapping
    public ResponseEntity<BillPayment> receivePayment(

            @RequestParam Long billId,

            @RequestBody BillPayment payment) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        billService.receivePayment(
                                billId,
                                payment
                        )
                );
    }

    /* ================================
       GET ALL PAYMENTS FOR BILL
    ================================= */

    @GetMapping("/bill/{billId}")
    public ResponseEntity<List<BillPayment>> getPayments(

            @PathVariable Long billId) {

        return ResponseEntity.ok(

                billService.getBillPayments(
                        billId
                )
        );
    }
}