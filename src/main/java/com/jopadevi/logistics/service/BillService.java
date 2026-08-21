package com.jopadevi.logistics.service;

import com.jopadevi.logistics.entity.Bill;
import com.jopadevi.logistics.entity.Company;
import com.jopadevi.logistics.entity.Trip;

import com.jopadevi.logistics.repository.BillRepository;
import com.jopadevi.logistics.repository.CompanyRepository;
import com.jopadevi.logistics.repository.TripRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    private final BillRepository billRepository;

    private final CompanyRepository companyRepository;

    private final TripRepository tripRepository;


    public BillService(
            BillRepository billRepository,
            CompanyRepository companyRepository,
            TripRepository tripRepository) {

        this.billRepository = billRepository;
        this.companyRepository = companyRepository;
        this.tripRepository = tripRepository;
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


        /* Default status */

        if (bill.getStatus() == null ||
                bill.getStatus().isBlank()) {

            bill.setStatus("PENDING");
        }


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

        Bill existingBill =
                getBillById(id);


        existingBill.setBillNumber(
                updatedBill.getBillNumber()
        );

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

        existingBill.setStatus(
                updatedBill.getStatus()
        );


        return billRepository.save(existingBill);
    }


    /* ================================
       DELETE BILL
    ================================= */

    public void deleteBill(Long id) {

        if (!billRepository.existsById(id)) {

            throw new RuntimeException(
                    "Bill not found"
            );
        }

        billRepository.deleteById(id);
    }

}