package com.jopadevi.logistics.service;

import com.jopadevi.logistics.entity.Bill;
import com.jopadevi.logistics.entity.BillPayment;
import com.jopadevi.logistics.repository.BillPaymentRepository;
import com.jopadevi.logistics.repository.BillRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BillPaymentService {

    private final BillPaymentRepository paymentRepository;
    private final BillRepository billRepository;

    public BillPaymentService(
            BillPaymentRepository paymentRepository,
            BillRepository billRepository) {

        this.paymentRepository = paymentRepository;
        this.billRepository = billRepository;
    }


    /* ================================
       RECORD PAYMENT
    ================================= */

    public BillPayment addPayment(
            Long billId,
            BillPayment payment) {

        /* Find bill */

        Bill bill = billRepository
                .findById(billId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Bill not found"
                        )
                );


        /* Validate payment amount */

        if (payment.getAmount() == null ||
                payment.getAmount()
                        .compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Payment amount must be greater than zero"
            );
        }


        /* Get remaining amount safely */

        BigDecimal currentRemaining =
                bill.getRemainingAmount() != null &&
                bill.getRemainingAmount().compareTo(BigDecimal.ZERO) > 0
                        ? bill.getRemainingAmount()
                        : bill.getTotalAmount()
                                .subtract(
                                        bill.getPaidAmount() != null
                                                ? bill.getPaidAmount()
                                                : BigDecimal.ZERO
                                );


        /* Prevent overpayment */

        if (payment.getAmount()
                .compareTo(currentRemaining) > 0) {

            throw new RuntimeException(
                    "Payment exceeds remaining bill amount. Remaining amount: ₹"
                            + currentRemaining
            );
        }


        /* Set bill */

        payment.setBill(bill);


        /* Default payment date */

        if (payment.getPaymentDate() == null) {

            payment.setPaymentDate(
                    LocalDate.now()
            );
        }


        /* Save payment */

        BillPayment savedPayment =
                paymentRepository.save(payment);


        /* Get current paid amount safely */

        BigDecimal currentPaid =
                bill.getPaidAmount() != null
                        ? bill.getPaidAmount()
                        : BigDecimal.ZERO;


        /* Calculate new paid amount */

        BigDecimal newPaidAmount =
                currentPaid.add(
                        payment.getAmount()
                );

        bill.setPaidAmount(
                newPaidAmount
        );


        /* Calculate remaining amount */

        BigDecimal newRemainingAmount =
                bill.getTotalAmount()
                        .subtract(
                                newPaidAmount
                        );

        bill.setRemainingAmount(
                newRemainingAmount
        );


        /* Update bill status */

        if (newRemainingAmount
                .compareTo(BigDecimal.ZERO) == 0) {

            bill.setStatus("PAID");

        } else if (newPaidAmount
                .compareTo(BigDecimal.ZERO) > 0) {

            bill.setStatus("PARTIALLY_PAID");

        } else {

            bill.setStatus("UNPAID");
        }


        /* Save updated bill */

        billRepository.save(bill);


        return savedPayment;
    }


    /* ================================
       GET PAYMENTS FOR BILL
    ================================= */

    public List<BillPayment> getPaymentsByBill(
            Long billId) {

        /* Check bill exists */

        if (!billRepository.existsById(billId)) {

            throw new RuntimeException(
                    "Bill not found"
            );
        }

        return paymentRepository
                .findByBillId(billId);
    }


    /* ================================
       TOTAL PAID
    ================================= */

    public BigDecimal getTotalPaid(
            Long billId) {

        Bill bill = billRepository
                .findById(billId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Bill not found"
                        )
                );

        return bill.getPaidAmount() != null
                ? bill.getPaidAmount()
                : BigDecimal.ZERO;
    }


    /* ================================
       REMAINING AMOUNT
    ================================= */

    public BigDecimal getRemainingAmount(
            Long billId) {

        Bill bill = billRepository
                .findById(billId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Bill not found"
                        )
                );

        return bill.getRemainingAmount() != null
                ? bill.getRemainingAmount()
                : bill.getTotalAmount();
    }
}