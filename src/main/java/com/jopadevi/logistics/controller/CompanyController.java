package com.jopadevi.logistics.controller;

import com.jopadevi.logistics.entity.Company;
import com.jopadevi.logistics.service.CompanyService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "http://localhost:5173")
public class CompanyController {

    private final CompanyService companyService;


    public CompanyController(
            CompanyService companyService) {

        this.companyService = companyService;
    }


    /* ================================
       ADD COMPANY
    ================================= */

    @PostMapping
    public ResponseEntity<Company> addCompany(
            @RequestBody Company company) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        companyService.addCompany(company)
                );
    }


    /* ================================
       GET ALL COMPANIES
    ================================= */

    @GetMapping
    public ResponseEntity<List<Company>>
    getAllCompanies() {

        return ResponseEntity.ok(
                companyService.getAllCompanies()
        );
    }


    /* ================================
       GET COMPANY BY ID
    ================================= */

    @GetMapping("/{id}")
    public ResponseEntity<Company>
    getCompanyById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                companyService.getCompanyById(id)
        );
    }


    /* ================================
       UPDATE COMPANY
    ================================= */

    @PutMapping("/{id}")
    public ResponseEntity<Company>
    updateCompany(
            @PathVariable Long id,
            @RequestBody Company company) {

        return ResponseEntity.ok(
                companyService.updateCompany(
                        id,
                        company
                )
        );
    }


    /* ================================
       DELETE COMPANY
    ================================= */

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deleteCompany(
            @PathVariable Long id) {

        companyService.deleteCompany(id);

        return ResponseEntity.ok(
                "Company deleted successfully"
        );
    }

}