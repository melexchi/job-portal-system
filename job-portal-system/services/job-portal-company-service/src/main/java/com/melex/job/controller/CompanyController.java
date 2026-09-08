package com.melex.job.controller;

import com.melex.job.domain.CompanyStatus;
import com.melex.job.domain.CompanyType;
import com.melex.job.domain.IndustryType;
import com.melex.job.dto.ApiResponse;
import com.melex.job.dto.CompanyRequest;
import com.melex.job.dto.CompanyResponse;
import com.melex.job.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponse>createCompany(@RequestHeader("X-User-Id") Long ownerId,
            @RequestBody @Valid CompanyRequest companyRequest) throws Exception {

       return ResponseEntity.status(HttpStatus.CREATED)
                .body(companyService.createCompany(ownerId,companyRequest));
    }


    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse>getCompanyById(@PathVariable Long id) throws Exception {

        return ResponseEntity.status(HttpStatus.OK)
                .body(companyService.getCompanyById(id));
    }


    @GetMapping("/me")
    public ResponseEntity<CompanyResponse> getMyCompany(@RequestHeader("X-User-Id") Long ownerId) throws Exception {

        return ResponseEntity.ok(companyService.getMyCompany(ownerId));
    }


    @GetMapping()
    public ResponseEntity<List<CompanyResponse>> getAllCompanies(@RequestParam(required = false)CompanyType companyType, @RequestParam(required = false) IndustryType industryType, @RequestParam(required = false) CompanyStatus status){

        return ResponseEntity.ok(companyService.getAllCompanies(companyType,industryType,status));

    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(@PathVariable Long id, @RequestHeader("X-User-Id") Long ownerId, @RequestBody @Valid CompanyRequest req) throws Exception {

        return ResponseEntity.ok(companyService.updatedCompany(id,ownerId,req));

    }

    @PatchMapping ("/{id}/verify")
    public ResponseEntity<CompanyResponse>verifyCompany(@PathVariable Long id){

        return ResponseEntity.ok(companyService.verifyCompany(id));
    }

    @PatchMapping("/{id}/deactivate")
    public  ResponseEntity<CompanyResponse>deactivateCompany(@PathVariable Long id){
        return ResponseEntity.ok(companyService.deactivateCompany(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCompany(@PathVariable Long id, @RequestHeader("X-User-Id") Long ownerId) throws Exception {

        companyService.deleteCompany(id, ownerId);
        return ResponseEntity.ok(new ApiResponse("Company deleted successfully", true));
    }



}
