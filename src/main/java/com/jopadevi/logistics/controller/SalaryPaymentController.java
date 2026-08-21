package com.jopadevi.logistics.controller;

import com.jopadevi.logistics.entity.SalaryPayment;
import com.jopadevi.logistics.service.SalaryPaymentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salary-payments")
public class SalaryPaymentController {


    private final SalaryPaymentService
            salaryPaymentService;


    public SalaryPaymentController(
            SalaryPaymentService salaryPaymentService) {

        this.salaryPaymentService =
                salaryPaymentService;
    }


    /* ================================
       RECORD PAYMENT
    ================================= */

    @PostMapping
    public ResponseEntity<SalaryPayment>
    recordPayment(

            @RequestParam Long driverId,

            @RequestBody
            SalaryPayment payment

    ) {

        SalaryPayment saved =
                salaryPaymentService
                    .recordPayment(
                        driverId,
                        payment
                    );


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }


    /* ================================
       GET ALL
    ================================= */

    @GetMapping
    public ResponseEntity<
        List<SalaryPayment>
    > getAllPayments() {

        return ResponseEntity.ok(
            salaryPaymentService
                .getAllPayments()
        );
    }


    /* ================================
       GET DRIVER PAYMENTS
    ================================= */

    @GetMapping(
        "/driver/{driverId}"
    )
    public ResponseEntity<
        List<SalaryPayment>
    > getDriverPayments(

            @PathVariable Long driverId

    ) {

        return ResponseEntity.ok(
            salaryPaymentService
                .getDriverPayments(
                    driverId
                )
        );
    }


    /* ================================
       GET BY ID
    ================================= */

    @GetMapping("/{id}")
    public ResponseEntity<SalaryPayment>
    getPayment(
            @PathVariable Long id) {

        return ResponseEntity.ok(
            salaryPaymentService
                .getPaymentById(id)
        );
    }


    /* ================================
       DELETE
    ================================= */

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deletePayment(
            @PathVariable Long id) {

        salaryPaymentService
                .deletePayment(id);


        return ResponseEntity.ok(
            "Salary payment deleted successfully"
        );
    }

}