package com.jopadevi.logistics.controller;

import com.jopadevi.logistics.entity.EMIPayment;
import com.jopadevi.logistics.service.EMIPaymentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/emi-payments")
@CrossOrigin(origins = "http://localhost:5173")
public class EMIPaymentController {

    private final EMIPaymentService paymentService;


    public EMIPaymentController(
            EMIPaymentService paymentService) {

        this.paymentService = paymentService;
    }


    /* ================================
       RECORD PAYMENT
    ================================= */

    @PostMapping
    public ResponseEntity<EMIPayment>
    recordPayment(

            @RequestParam Long emiId,

            @RequestBody EMIPayment payment) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        paymentService.recordPayment(
                                emiId,
                                payment
                        )
                );
    }


    /* ================================
       GET PAYMENT HISTORY
    ================================= */

    @GetMapping("/emi/{emiId}")
    public ResponseEntity<List<EMIPayment>>
    getPayments(
            @PathVariable Long emiId) {

        return ResponseEntity.ok(
                paymentService
                        .getPaymentsByEMI(emiId)
        );
    }


    /* ================================
       TOTAL PAID
    ================================= */

    @GetMapping("/emi/{emiId}/total-paid")
    public ResponseEntity<BigDecimal>
    getTotalPaid(
            @PathVariable Long emiId) {

        return ResponseEntity.ok(
                paymentService
                        .getTotalPaid(emiId)
        );
    }


    /* ================================
       REMAINING EMIs
    ================================= */

    @GetMapping("/emi/{emiId}/remaining")
    public ResponseEntity<Integer>
    getRemainingEMIs(
            @PathVariable Long emiId) {

        return ResponseEntity.ok(
                paymentService
                        .getRemainingEMIs(emiId)
        );
    }


    /* ================================
       OUTSTANDING
    ================================= */

    @GetMapping("/emi/{emiId}/outstanding")
    public ResponseEntity<BigDecimal>
    getOutstanding(
            @PathVariable Long emiId) {

        return ResponseEntity.ok(
                paymentService
                        .getOutstandingAmount(emiId)
        );
    }

}