package com.jopadevi.logistics.controller;

import com.jopadevi.logistics.entity.BillPayment;
import com.jopadevi.logistics.service.BillPaymentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/bill-payments")
@CrossOrigin(origins = "http://localhost:5173")
public class BillPaymentController {

    private final BillPaymentService paymentService;


    public BillPaymentController(
            BillPaymentService paymentService) {

        this.paymentService = paymentService;
    }


    /* ================================
       ADD PAYMENT
    ================================= */

    @PostMapping
    public ResponseEntity<BillPayment>
    addPayment(

            @RequestParam Long billId,

            @RequestBody BillPayment payment) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        paymentService.addPayment(
                                billId,
                                payment
                        )
                );
    }


    /* ================================
       GET PAYMENTS FOR BILL
    ================================= */

    @GetMapping("/bill/{billId}")
    public ResponseEntity<List<BillPayment>>
    getPayments(
            @PathVariable Long billId) {

        return ResponseEntity.ok(
                paymentService
                        .getPaymentsByBill(billId)
        );
    }


    /* ================================
       TOTAL RECEIVED
    ================================= */

    @GetMapping("/bill/{billId}/total")
    public ResponseEntity<BigDecimal>
    getTotalPaid(
            @PathVariable Long billId) {

        return ResponseEntity.ok(
                paymentService
                        .getTotalPaid(billId)
        );
    }


    /* ================================
       REMAINING
    ================================= */

    @GetMapping("/bill/{billId}/remaining")
    public ResponseEntity<BigDecimal>
    getRemaining(
            @PathVariable Long billId) {

        return ResponseEntity.ok(
                paymentService
                        .getRemainingAmount(billId)
        );
    }

}