package com.teja.app.service.impl;

import com.teja.app.domain.Company;
import com.teja.app.repository.CompanyRepository;
import com.teja.app.service.CompanyService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Company}.
 */
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company save(Company company) {
        log.debug("Request to save Company : {}", company);
        return companyRepository.save(company);
    }

    @Override
    public Optional<Company> partialUpdate(Company company) {
        log.debug("Request to partially update Company : {}", company);

        return companyRepository
            .findById(company.getId())
            .map(
                existingCompany -> {
                    if (company.getCompanyName() != null) {
                        existingCompany.setCompanyName(company.getCompanyName());
                    }
                    if (company.getStartDate() != null) {
                        existingCompany.setStartDate(company.getStartDate());
                    }
                    if (company.getEndDate() != null) {
                        existingCompany.setEndDate(company.getEndDate());
                    }
                    if (company.getCompanyDetails() != null) {
                        existingCompany.setCompanyDetails(company.getCompanyDetails());
                    }
                    if (company.getPlacementType() != null) {
                        existingCompany.setPlacementType(company.getPlacementType());
                    }
                    if (company.getSalaryPackage() != null) {
                        existingCompany.setSalaryPackage(company.getSalaryPackage());
                    }
                    if (company.getStage() != null) {
                        existingCompany.setStage(company.getStage());
                    }

                    return existingCompany;
                }
            )
            .map(companyRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Company> findAll() {
        log.debug("Request to get all Companies");
        return companyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Company> findOne(Long id) {
        log.debug("Request to get Company : {}", id);
        return companyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Company : {}", id);
        companyRepository.deleteById(id);
    }
}
