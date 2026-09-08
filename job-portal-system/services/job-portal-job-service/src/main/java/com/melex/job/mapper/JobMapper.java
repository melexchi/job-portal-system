package com.melex.job.mapper;

import com.melex.job.dto.CompanyResponse;
import com.melex.job.dto.JobResponse;
import com.melex.job.model.Job;
import com.melex.job.model.embeddable.JobLocation;
import com.melex.job.model.embeddable.SalaryRange;

public class JobMapper {


    public static JobResponse toResponse(Job job, CompanyResponse companyResponse){

        JobLocation loc = job.getLocation();
        SalaryRange sal = job.getSalaryRange();

        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .requirements(job.getRequirements())
                .responsibilities(job.getResponsibilities())
                .benefits(job.getBenefits())
                .company(companyResponse)
//                .category(toCategoryResponse(job.getCategory))
//                .skills(skills)
//                .tags(tags)
                .address(loc != null ? loc.getAddress() : null)
                .city(loc != null ? loc.getCity():null)
                .state(loc != null ? loc.getState() :null)
                .country(loc != null ? loc.getCountry() : null)
                .zipCode(loc != null ? loc.getZipCode():null)
                .maxSalary(sal !=null ? sal.getMaxSalary():null)
                .jobType(job.getJobType())
                .workMode(job.getWorkMode())
                .experienceLevel(job.getExperienceLevel())
                .status(job.getStatus())
                .openings(job.getOpenings())
                .applicationDeadline(job.getApplicationDeadline())
                .expiresAt(job.getExpiresAt())
                .active(job.getActive())
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .publishedAt(job.getPublishedAt())
                .closedAt(job.getClosedAt())
                .build();
    }
}
