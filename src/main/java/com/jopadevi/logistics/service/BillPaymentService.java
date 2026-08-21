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


        Bill bill = billRepository
                .findById(billId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Bill not found"
                        )
                );


        /* Payment amount must be positive */

        if (payment.getAmount() == null ||
                payment.getAmount()
                        .compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Payment amount must be greater than zero"
            );
        }


        /* Calculate already received */

        BigDecimal alreadyPaid =
                getTotalPaid(billId);


        /* Calculate remaining */

        BigDecimal remaining =
                bill.getTotalAmount()
                        .subtract(alreadyPaid);


        /* Don't allow overpayment */

        if (payment.getAmount()
                .compareTo(remaining) > 0) {

            throw new RuntimeException(
                    "Payment exceeds remaining bill amount. Remaining amount: ₹"
                            + remaining
            );
        }


        payment.setBill(bill);


        /* Default payment date */

        if (payment.getPaymentDate() == null) {

            payment.setPaymentDate(
                    LocalDate.now()
            );
        }


        BillPayment savedPayment =
                paymentRepository.save(payment);


        /* Update bill status */

        updateBillStatus(bill);


        return savedPayment;
    }


    /* ================================
       GET PAYMENTS
    ================================= */

    public List<BillPayment> getPaymentsByBill(
            Long billId) {

        return paymentRepository
                .findByBillId(billId);
    }


    /* ================================
       TOTAL PAID
    ================================= */

    public BigDecimal getTotalPaid(
            Long billId) {

        List<BillPayment> payments =
                paymentRepository
                        .findByBillId(billId);


        return payments.stream()

                .map(BillPayment::getAmount)

                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );
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


        BigDecimal paid =
                getTotalPaid(billId);


        return bill.getTotalAmount()
                .subtract(paid);
    }


    /* ================================
       UPDATE BILL STATUS
    ================================= */

    private void updateBillStatus(
            Bill bill) {

        BigDecimal paid =
                getTotalPaid(bill.getId());


        if (paid.compareTo(BigDecimal.ZERO) == 0) {

            bill.setStatus("PENDING");

        }

        else if (paid.compareTo(
                bill.getTotalAmount()) >= 0) {

            bill.setStatus("PAID");

        }

        else {

            bill.setStatus("PARTIALLY_PAID");

        }


        billRepository.save(bill);
    }

}