package com.jopadevi.logistics.service;

import com.jopadevi.logistics.entity.EMIPayment;
import com.jopadevi.logistics.entity.VehicleEMI;

import com.jopadevi.logistics.repository.EMIPaymentRepository;
import com.jopadevi.logistics.repository.VehicleEMIRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class EMIPaymentService {

    private final EMIPaymentRepository paymentRepository;

    private final VehicleEMIRepository emiRepository;


    public EMIPaymentService(
            EMIPaymentRepository paymentRepository,
            VehicleEMIRepository emiRepository) {

        this.paymentRepository = paymentRepository;
        this.emiRepository = emiRepository;
    }


    /* ================================
       RECORD EMI PAYMENT
    ================================= */

    public EMIPayment recordPayment(
            Long emiId,
            EMIPayment payment) {


        VehicleEMI emi =
                emiRepository.findById(emiId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vehicle EMI not found"
                                )
                        );


        /* Validate EMI number */

        if (payment.getEmiNumber() == null ||
                payment.getEmiNumber() <= 0) {

            throw new RuntimeException(
                    "EMI number must be greater than zero"
            );
        }


        /* Check duplicate EMI payment */

        if (paymentRepository
                .existsByVehicleEMIIdAndEmiNumber(
                        emiId,
                        payment.getEmiNumber()
                )) {

            throw new RuntimeException(
                    "This EMI has already been recorded"
            );
        }


        /* Don't allow EMI beyond total */

        if (payment.getEmiNumber()
                > emi.getTotalEMIs()) {

            throw new RuntimeException(
                    "EMI number exceeds total EMI count"
            );
        }


        /* Payment amount */

        if (payment.getAmount() == null ||
                payment.getAmount()
                        .compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Payment amount must be greater than zero"
            );
        }


        payment.setVehicleEMI(emi);


        /* Default payment date */

        if (payment.getPaymentDate() == null) {

            payment.setPaymentDate(
                    LocalDate.now()
            );
        }


        EMIPayment savedPayment =
                paymentRepository.save(payment);


        /* Update paid EMI count */

        updatePaidEMIs(emi);


        return savedPayment;
    }


    /* ================================
       UPDATE PAID EMI COUNT
    ================================= */

    private void updatePaidEMIs(
            VehicleEMI emi) {

    	int currentPaidEMIs =
    	        emi.getPaidEMIs() == null
    	                ? 0
    	                : emi.getPaidEMIs();

    	emi.setPaidEMIs(
    	        currentPaidEMIs + 1
    	);
        /* Update EMI status */

    	if (emi.getPaidEMIs() >=
    	        emi.getTotalEMIs()) {

    	    emi.setStatus("COMPLETED");

    	} else {

    	    emi.setStatus("ACTIVE");
    	}


        emiRepository.save(emi);
    }


    /* ================================
       GET PAYMENTS
    ================================= */

    public List<EMIPayment>
    getPaymentsByEMI(Long emiId) {

        if (!emiRepository.existsById(emiId)) {

            throw new RuntimeException(
                    "Vehicle EMI not found"
            );
        }


        return paymentRepository
                .findByVehicleEMIId(emiId);
    }


    /* ================================
       TOTAL PAID AMOUNT
    ================================= */

    public BigDecimal getTotalPaid(
            Long emiId) {

        List<EMIPayment> payments =
                getPaymentsByEMI(emiId);


        return payments.stream()

                .map(EMIPayment::getAmount)

                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );
    }


    /* ================================
       REMAINING EMI COUNT
    ================================= */

    public int getRemainingEMIs(
            Long emiId) {

        VehicleEMI emi =
                emiRepository.findById(emiId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vehicle EMI not found"
                                )
                        );


        return emi.getTotalEMIs()
                - emi.getPaidEMIs();
    }


    /* ================================
       OUTSTANDING AMOUNT
    ================================= */

    public BigDecimal getOutstandingAmount(
            Long emiId) {

        VehicleEMI emi =
                emiRepository.findById(emiId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vehicle EMI not found"
                                )
                        );


        BigDecimal totalPayable =
                emi.getMonthlyEMI()
                        .multiply(
                                BigDecimal.valueOf(
                                        emi.getTotalEMIs()
                                )
                        );


        BigDecimal paid =
                getTotalPaid(emiId);


        return totalPayable.subtract(paid);
    }

}