package com.melex.job.mapper;

import com.melex.job.dto.CompanyResponse;
import com.melex.job.dto.SocialLinkResponse;
import com.melex.job.model.Company;
import com.melex.job.model.SocialLink;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyMapper {

    public static SocialLinkResponse toSocialLinkResponse(SocialLink socialLinks){
        return SocialLinkResponse.builder()
                .platform(socialLinks.getPlatform())
                .url(socialLinks.getUrl())
                .build();
    }

    public static CompanyResponse toResponse(Company company) {

        List<SocialLinkResponse> socialLinks =company.getSocialLinks() ==null? Collections.emptyList(): company.getSocialLinks().stream().map(CompanyMapper::toSocialLinkResponse).collect(Collectors.toList());

        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .slug(company.getSlug())
                .tagline(company.getTagline())
                .description(company.getDescription())
                .logoUrl(company.getLogoUrl())
                .coverImageUrl(company.getCoverImageUrl())
                .website(company.getWebsite())
                .email(company.getEmail())
                .phone(company.getPhone())
                .foundedYear(company.getFoundedYear())
                .companySize(company.getCompanySize())
                .companyType(company.getCompanyType())
                .industryType(company.getIndustryType())
                .status(company.getStatus())
                .active(company.getActive())
                .ownerId(company.getOwnerId())
                .socialLinks(socialLinks)
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                .build();

    }


}
