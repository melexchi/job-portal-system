package com.melex.job.dto;

import com.melex.job.domain.CompanySize;
import com.melex.job.domain.CompanyType;
import com.melex.job.domain.IndustryType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRequest {
    @NotBlank(message = "Company name is required")
    private String name;
    private String tagline;
    private String description;
    private String logoUrl;
    private String coverImageUrl;

    @Pattern(regexp = "^(https?://)?(www\\.)?([\\w-]+)\\.([a-z]{2,})(/[\\w-./?%&=]*)?$", message = "Invalid website URL")
    private String website;


    @Min(value = 1800, message = "Founded year must be greater than or equal to 1800")
    @Max(value = 2100,message = "Founded year must be less than or equal to 2100")
    private Integer foundedYear;


    @Email(message = "Invalid email address")
    private String email;

    private String phone;

    private String registrationNumber;

    @NotNull(message = "Company size is required")
    private CompanySize companySize;

    @NotNull(message = "Company type is required")
    private CompanyType companyType;

    @NotNull(message = "Industry type is required")
    private IndustryType industryType;



    private List<SocialLinkResponse> socialLinks;
}
