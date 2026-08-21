package com.jopadevi.logistics.repository;

import com.jopadevi.logistics.entity.Company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository
        extends JpaRepository<Company, Long> {

    boolean existsByCompanyName(String companyName);

}