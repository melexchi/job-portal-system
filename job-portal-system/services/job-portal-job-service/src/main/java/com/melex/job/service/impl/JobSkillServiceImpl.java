package com.melex.job.service.impl;

import com.melex.job.dto.JobSkillResponse;
import com.melex.job.model.JobSkill;
import com.melex.job.payload.JobSkillRequest;
import com.melex.job.repository.JobSkillRepository;
import com.melex.job.service.JobSkillService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class JobSkillServiceImpl implements JobSkillService {

    private final JobSkillRepository jobSkillRepository;


    @Override
    public JobSkillResponse createSkill(JobSkillRequest req) throws Exception {

        if(jobSkillRepository.existsByName(req.getName())){
            throw new Exception("Skill name already exists");
        }

        String slug =generateUniqueSlug(req.getName());


        JobSkill skill = JobSkill.builder()
                .name(req.getName())
                .slug(slug)
                .category(req.getCategory())
                .build();


        JobSkill savedSkill = jobSkillRepository.save(skill)

        return null;
    }

    @Override
    public List<JobSkillResponse> getAllSkills() {
        return List.of();
    }

    @Override
    public JobSkillResponse getSkillById(Long id) {
        return null;
    }

    @Override
    public JobSkillResponse updateSkill(Long id, JobSkillRequest req) {
        return null;
    }

    @Override
    public void deleteSkill(Long id) {

    }

    @Override
    public Set<JobSkill> getSkillsByIds(Set<Long> ids) {
        return Set.of();
    }



    private String generateUniqueSlug(@NotBlank(message = "Company name is required") String name) {
        String base =name.toLowerCase().replaceAll("[^a-z0-9\\s-]+", "").trim().replaceAll("[\\s-]+", "-");

        if(!jobSkillRepository.existsBySlug(base)){
            return base;
        }
        int counter = 1;

        while(jobSkillRepository.existsBySlug(base+ "-"+counter)){
            counter++;
        }

        return base + "-" + counter;
    }
}
