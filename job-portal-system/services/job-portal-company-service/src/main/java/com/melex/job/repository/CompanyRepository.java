package com.melex.job.repository;

import com.melex.job.domain.CompanyStatus;
import com.melex.job.domain.CompanyType;
import com.melex.job.domain.IndustryType;
import com.melex.job.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByOwnerId(Long ownerId);

    boolean existsByOwnerId(Long ownerId);

    boolean existsByName(String name);

    boolean existsBySlug(String slug);

    boolean existsByRegistrationNumber(String registrationNumber);

    @Query(
            "select c from Company c where" + "(:companyType Is Null OR c.companyType=:companyType) AND " +"(:industryType Is Null OR c.industryType=:industryType) AND " + "(:status Is Null Or c.status=:status)")
    List<Company> findByFilters(@Param("companyType") CompanyType companyType, @Param("industryType")IndustryType industryType, @Param("status")CompanyStatus status);


}
