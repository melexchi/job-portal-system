package com.melex.job.service;

import com.melex.job.domain.CompanyStatus;
import com.melex.job.domain.CompanyType;
import com.melex.job.domain.IndustryType;
import com.melex.job.dto.CompanyRequest;
import com.melex.job.dto.CompanyResponse;
import com.melex.job.model.Company;

import java.util.List;

public interface CompanyService {

    CompanyResponse createCompany(Long ownerId, CompanyRequest req) throws Exception;

    CompanyResponse getCompanyById(Long id) throws Exception;

    CompanyResponse getMyCompany(Long ownerId) throws Exception;

    List<CompanyResponse> getAllCompanies(CompanyType companyType, IndustryType industryType, CompanyStatus companyStatus
    );

    CompanyResponse updatedCompany(Long ownerId, Long companyId, CompanyRequest req) throws Exception;

    CompanyResponse verifyCompany(Long companyId);

    CompanyResponse deactivateCompany(Long companyId);

//    CompanyResponse activateCompany(Long companyId);

    void deleteCompany(Long ownerId, Long companyId) throws Exception;

    Company getCompanyEntityById(Long id);
}
