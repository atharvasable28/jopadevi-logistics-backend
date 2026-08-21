package com.jopadevi.logistics.service;

import com.jopadevi.logistics.entity.Company;
import com.jopadevi.logistics.repository.CompanyRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;


    public CompanyService(
            CompanyRepository companyRepository) {

        this.companyRepository = companyRepository;
    }


    /* ================================
       ADD COMPANY
    ================================= */

    public Company addCompany(Company company) {

        if (companyRepository.existsByCompanyName(
                company.getCompanyName())) {

            throw new RuntimeException(
                    "Company already exists"
            );
        }

        if (company.getStatus() == null ||
                company.getStatus().isBlank()) {

            company.setStatus("ACTIVE");
        }

        return companyRepository.save(company);
    }


    /* ================================
       GET ALL COMPANIES
    ================================= */

    public List<Company> getAllCompanies() {

        return companyRepository.findAll();
    }


    /* ================================
       GET COMPANY
    ================================= */

    public Company getCompanyById(Long id) {

        return companyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Company not found"
                        )
                );
    }


    /* ================================
       UPDATE COMPANY
    ================================= */

    public Company updateCompany(
            Long id,
            Company updatedCompany) {

        Company existingCompany =
                getCompanyById(id);


        existingCompany.setCompanyName(
                updatedCompany.getCompanyName()
        );

        existingCompany.setContactPerson(
                updatedCompany.getContactPerson()
        );

        existingCompany.setPhoneNumber(
                updatedCompany.getPhoneNumber()
        );

        existingCompany.setEmail(
                updatedCompany.getEmail()
        );

        existingCompany.setGstNumber(
                updatedCompany.getGstNumber()
        );

        existingCompany.setAddress(
                updatedCompany.getAddress()
        );

        existingCompany.setCity(
                updatedCompany.getCity()
        );

        existingCompany.setState(
                updatedCompany.getState()
        );

        existingCompany.setStatus(
                updatedCompany.getStatus()
        );


        return companyRepository.save(
                existingCompany
        );
    }


    /* ================================
       DELETE COMPANY
    ================================= */

    public void deleteCompany(Long id) {

        if (!companyRepository.existsById(id)) {

            throw new RuntimeException(
                    "Company not found"
            );
        }

        companyRepository.deleteById(id);
    }

}