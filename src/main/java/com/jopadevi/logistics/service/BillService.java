package com.jopadevi.logistics.service;

import com.jopadevi.logistics.entity.Bill;
import com.jopadevi.logistics.entity.BillPayment;
import com.jopadevi.logistics.entity.Company;
import com.jopadevi.logistics.entity.Trip;

import com.jopadevi.logistics.repository.BillRepository;
import com.jopadevi.logistics.repository.BillPaymentRepository;
import com.jopadevi.logistics.repository.CompanyRepository;
import com.jopadevi.logistics.repository.TripRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
//import org.springframework.transaction.annotation.Transactional;




@Service
public class BillService {

    private final BillRepository billRepository;

    private final CompanyRepository companyRepository;

    private final TripRepository tripRepository;

    private final BillPaymentRepository billPaymentRepository;


    public BillService(
            BillRepository billRepository,
            CompanyRepository companyRepository,
            TripRepository tripRepository,
            BillPaymentRepository billPaymentRepository) {

        this.billRepository = billRepository;
        this.companyRepository = companyRepository;
        this.tripRepository = tripRepository;
        this.billPaymentRepository = billPaymentRepository;
    }
    
    


    /* ================================
       ADD BILL
    ================================= */

    public Bill addBill(
            Bill bill,
            Long companyId,
            Long tripId) {

        /* Check duplicate bill number */

        if (billRepository.existsByBillNumber(
                bill.getBillNumber())) {

            throw new RuntimeException(
                    "Bill number already exists"
            );
        }


        /* Find company */

        Company company =
                companyRepository.findById(companyId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Company not found"
                                )
                        );

        bill.setCompany(company);


        /* Find trip if provided */

        if (tripId != null) {

            Trip trip =
                    tripRepository.findById(tripId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Trip not found"
                                    )
                            );

            bill.setTrip(trip);
        }


        /* Validate total amount */

        if (bill.getTotalAmount() == null ||
                bill.getTotalAmount()
                        .compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Total amount must be greater than zero"
            );
        }


        /* Initialize payment values */

        bill.setPaidAmount(BigDecimal.ZERO);

        bill.setRemainingAmount(
                bill.getTotalAmount()
        );


        /* New bill is unpaid */

        bill.setStatus("UNPAID");


        return billRepository.save(bill);
    }


    /* ================================
       GET ALL BILLS
    ================================= */

    public List<Bill> getAllBills() {

        return billRepository.findAll();
    }


    /* ================================
       GET BILL BY ID
    ================================= */

    public Bill getBillById(Long id) {

        return billRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Bill not found"
                        )
                );
    }


    /* ================================
    UPDATE BILL
 ================================= */

 public Bill updateBill(
         Long id,
         Bill updatedBill) {

     Bill existingBill = getBillById(id);

     /* Update basic information */

     existingBill.setBillNumber(
             updatedBill.getBillNumber()
     );

     /* Validate total amount */

     if (updatedBill.getTotalAmount() == null ||
             updatedBill.getTotalAmount()
                     .compareTo(BigDecimal.ZERO) <= 0) {

         throw new RuntimeException(
                 "Total amount must be greater than zero"
         );
     }

     /* Prevent total from becoming less than paid amount */

     BigDecimal currentPaid =
             existingBill.getPaidAmount() != null
                     ? existingBill.getPaidAmount()
                     : BigDecimal.ZERO;

     if (updatedBill.getTotalAmount()
             .compareTo(currentPaid) < 0) {

         throw new RuntimeException(
                 "Total amount cannot be less than the amount already paid"
         );
     }

     existingBill.setTotalAmount(
             updatedBill.getTotalAmount()
     );

     existingBill.setBillDate(
             updatedBill.getBillDate()
     );

     existingBill.setDueDate(
             updatedBill.getDueDate()
     );

     existingBill.setDescription(
             updatedBill.getDescription()
     );

     /* Recalculate remaining amount */

     BigDecimal newRemainingAmount =
             existingBill.getTotalAmount()
                     .subtract(currentPaid);

     existingBill.setRemainingAmount(
             newRemainingAmount
     );

     /* Update status automatically */

     if (newRemainingAmount.compareTo(BigDecimal.ZERO) == 0) {

         existingBill.setStatus("PAID");

     } else if (currentPaid.compareTo(BigDecimal.ZERO) > 0) {

         existingBill.setStatus("PARTIALLY_PAID");

     } else {

         existingBill.setStatus("UNPAID");
     }

     return billRepository.save(existingBill);
 }
    /* ================================
       RECEIVE BILL PAYMENT
    ================================= */

    public BillPayment receivePayment(
            Long billId,
            BillPayment payment) {

        /* Find bill */

        Bill bill = getBillById(billId);


        /* Validate payment amount */

        if (payment.getAmount() == null ||
                payment.getAmount()
                        .compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Payment amount must be greater than zero"
            );
        }


        /* Get current remaining amount safely */

        BigDecimal currentRemaining =
                bill.getRemainingAmount() != null
                        ? bill.getRemainingAmount()
                        : bill.getTotalAmount();


        /* Prevent overpayment */

        if (payment.getAmount()
                .compareTo(currentRemaining) > 0) {

            throw new RuntimeException(
                    "Payment amount cannot be greater than remaining amount"
            );
        }


        /* Set bill for payment */

        payment.setBill(bill);


        /* Set today's date if not provided */

        if (payment.getPaymentDate() == null) {

            payment.setPaymentDate(
                    LocalDate.now()
            );
        }


        /* Save payment */

        BillPayment savedPayment =
                billPaymentRepository.save(payment);


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

        BigDecimal remainingAmount =
                bill.getTotalAmount()
                        .subtract(
                                newPaidAmount
                        );

        bill.setRemainingAmount(
                remainingAmount
        );


        /* Update bill status */

        if (remainingAmount.compareTo(
                BigDecimal.ZERO) == 0) {

            bill.setStatus("PAID");

        } else if (newPaidAmount.compareTo(
                BigDecimal.ZERO) > 0) {

            bill.setStatus("PARTIALLY_PAID");

        } else {

            bill.setStatus("UNPAID");
        }


        /* Save updated bill */

        billRepository.save(bill);


        return savedPayment;
    }


    /* ================================
       GET PAYMENTS FOR A BILL
    ================================= */

    public List<BillPayment> getBillPayments(
            Long billId) {

        /* Make sure bill exists */

        getBillById(billId);


        return billPaymentRepository
                .findByBillId(billId);
    }


    /* ================================
    DELETE BILL
 ================================= */

 @Transactional
 public void deleteBill(Long id) {

     Bill bill = billRepository.findById(id)
             .orElseThrow(() ->
                     new RuntimeException(
                             "Bill not found"
                     )
             );

     // Delete all payments related to this bill first
     billPaymentRepository.deleteById(id);

     // Then delete the bill
     billRepository.delete(bill);
 }
}