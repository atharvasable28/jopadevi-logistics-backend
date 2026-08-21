package com.jopadevi.logistics.service;

import com.jopadevi.logistics.entity.Driver;
import com.jopadevi.logistics.entity.SalaryPayment;
import com.jopadevi.logistics.repository.DriverRepository;
import com.jopadevi.logistics.repository.SalaryPaymentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryPaymentService {

    private final SalaryPaymentRepository
            salaryPaymentRepository;

    private final DriverRepository
            driverRepository;


    public SalaryPaymentService(
            SalaryPaymentRepository salaryPaymentRepository,
            DriverRepository driverRepository) {

        this.salaryPaymentRepository =
                salaryPaymentRepository;

        this.driverRepository =
                driverRepository;
    }


    /* ================================
       RECORD PAYMENT
    ================================= */

    public SalaryPayment recordPayment(
            Long driverId,
            SalaryPayment payment) {

        Driver driver =
                driverRepository
                    .findById(driverId)
                    .orElseThrow(() ->
                        new RuntimeException(
                            "Driver not found"
                        )
                    );


        payment.setDriver(driver);


        if (payment.getAmount() == null ||
            payment.getAmount() <= 0) {

            throw new RuntimeException(
                "Payment amount must be greater than zero"
            );

        }


        return salaryPaymentRepository
                .save(payment);
    }


    /* ================================
       GET ALL PAYMENTS
    ================================= */

    public List<SalaryPayment>
    getAllPayments() {

        return salaryPaymentRepository
                .findAll();
    }


    /* ================================
       GET DRIVER PAYMENTS
    ================================= */

    public List<SalaryPayment>
    getDriverPayments(
            Long driverId) {

        return salaryPaymentRepository
                .findByDriverId(
                    driverId
                );
    }


    /* ================================
       GET PAYMENT
    ================================= */

    public SalaryPayment
    getPaymentById(Long id) {

        return salaryPaymentRepository
                .findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Salary payment not found"
                    )
                );
    }


    /* ================================
       DELETE PAYMENT
    ================================= */

    public void deletePayment(Long id) {

        if (
            !salaryPaymentRepository
                .existsById(id)
        ) {

            throw new RuntimeException(
                "Salary payment not found"
            );

        }


        salaryPaymentRepository
                .deleteById(id);

    }

}