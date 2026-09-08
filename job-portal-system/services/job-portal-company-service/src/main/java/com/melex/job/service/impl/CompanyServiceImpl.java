package com.melex.job.service.impl;

import com.melex.job.domain.CompanyStatus;
import com.melex.job.domain.CompanyType;
import com.melex.job.domain.IndustryType;
import com.melex.job.dto.CompanyRequest;
import com.melex.job.dto.CompanyResponse;
import com.melex.job.dto.SocialLinkResponse;
import com.melex.job.mapper.CompanyMapper;
import com.melex.job.model.Company;
import com.melex.job.model.SocialLink;
import com.melex.job.repository.CompanyRepository;
import com.melex.job.service.CompanyService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public CompanyResponse createCompany(Long ownerId, CompanyRequest req) throws Exception {

        if(companyRepository.existsByOwnerId(ownerId)){
            throw  new Exception("You already have a company registered. " + "only one company per account is allowed");
        }
        if(companyRepository.existsByName(req.getName())){
            throw new Exception("Company name already exists. Please choose a different name.");
        }

        if(req.getRegistrationNumber() !=null &&  companyRepository.existsByRegistrationNumber(req.getRegistrationNumber())){
            throw new Exception("Company registration number already exists. Please choose a different registration number.");
        }

        String slug= generateUniqueSlug(req.getName());

        Company company = Company.builder()
                .name(req.getName())
                .slug(slug)
                .tagline(req.getTagline())
                .description(req.getDescription())
                .logoUrl(req.getLogoUrl())
                .coverImageUrl(req.getCoverImageUrl())
                .website(req.getWebsite())
                .email(req.getEmail())
                .phone(req.getPhone())
                .foundedYear(req.getFoundedYear())
                .companySize(req.getCompanySize())
                .companyType(req.getCompanyType())
                .industryType(req.getIndustryType())
                .registrationNumber(req.getRegistrationNumber())
                .ownerId(ownerId)
                .socialLinks(mapSocialLinks(req.getSocialLinks()))
                .build();

       Company savedCompany= companyRepository.save(company);

        return CompanyMapper.toResponse(savedCompany);
    }

    private List<SocialLink> mapSocialLinks(List<SocialLinkResponse> socialLinks) {
        if(socialLinks == null || socialLinks.isEmpty()){
            return new ArrayList<>();
        }
        return socialLinks.stream().map(e->SocialLink.builder()
                .platform(e.getPlatform())
                .url(e.getUrl())
                .build())
                .collect(Collectors.toList());
    }

    private String generateUniqueSlug(@NotBlank(message = "Company name is required") String name) {
        String base =name.toLowerCase().replaceAll("[^a-z0-9\\s-]+", "").trim().replaceAll("\\s-", "-");

        if(!companyRepository.existsBySlug(base)){
            return base;
        }
        int counter = 1;

        while(companyRepository.existsBySlug(base+ "-"+counter)){
            counter++;
        }

        return base + "-" + counter;
    }

    @Override
    public CompanyResponse getCompanyById(Long id) throws Exception {
        Company company =companyRepository.findById(id).orElseThrow(()-> new Exception("Company not found with id: " + id));

        return CompanyMapper.toResponse(company);
    }

    @Override
    public CompanyResponse getMyCompany(Long ownerId) throws Exception {

        Company company = companyRepository.findByOwnerId(ownerId).orElseThrow(()-> new Exception("Company not found for owner with id: " + ownerId));
        return CompanyMapper.toResponse(company);
    }

    @Override
    public List<CompanyResponse> getAllCompanies(CompanyType companyType, IndustryType industryType, CompanyStatus companyStatus) {


        return companyRepository.findByFilters(companyType,industryType,companyStatus).stream().map(CompanyMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public CompanyResponse updatedCompany(Long ownerId, Long companyId, CompanyRequest req) throws Exception {
        Company company =getCompanyEntityById(companyId);

        if(!company.getName().equals(req.getName())&& companyRepository.existsByName(req.getName())){
            throw new Exception("Company name already exists. Please choose a different name.");
        }

        if(req.getRegistrationNumber()!=null && !req.getRegistrationNumber().equals(company.getRegistrationNumber())&& !companyRepository.existsByRegistrationNumber(company.getRegistrationNumber())){
            throw  new Exception("Company registration number already exists. Please choose a different registration number.");
        }

        company.setName(req.getName());
        company.setTagline(req.getTagline());
        company.setDescription(req.getDescription());
        company.setLogoUrl(req.getLogoUrl());
        company.setCoverImageUrl(req.getCoverImageUrl());
        company.setWebsite(req.getWebsite());
        company.setEmail(req.getEmail());
        company.setPhone(req.getPhone());
        company.setFoundedYear(req.getFoundedYear());
        company.setCompanySize(req.getCompanySize());
        company.setCompanyType(req.getCompanyType());
        company.setIndustryType(req.getIndustryType());
        company.setRegistrationNumber(req.getRegistrationNumber());
        company.setSocialLinks(mapSocialLinks(req.getSocialLinks()));


        return CompanyMapper.toResponse(companyRepository.save(company));
    }

    @Override
    public CompanyResponse verifyCompany(Long companyId) {

        Company company = getCompanyEntityById(companyId);
        company.setStatus(CompanyStatus.ACTIVE);
        company.setVerified(true);

        return CompanyMapper.toResponse(companyRepository.save(company));
    }

    @Override
    public CompanyResponse deactivateCompany(Long companyId) {

        Company company = getCompanyEntityById(companyId);
        company.setStatus(CompanyStatus.SUSPENDED);
        company.setVerified(false);
        return CompanyMapper.toResponse(companyRepository.save(company));
    }

    @Override
    public void deleteCompany(Long ownerId, Long companyId) throws Exception {

        Company company =getCompanyEntityById(companyId);

        assertOwner(company,ownerId);

         companyRepository.delete(company);

    }

    private void assertOwner(Company company, Long ownerId) throws Exception {
        if(!company.getOwnerId().equals(ownerId)){
            throw new Exception("You are not authorized to delete this company.");
        }
    }

    @Override
    public Company getCompanyEntityById(Long id) {

      return companyRepository.findById(id).orElseThrow(()-> new RuntimeException("Company not found with id: " + id));
    }
}
