package com.melex.job.service;


import com.melex.job.dto.JobSkillResponse;
import com.melex.job.model.JobSkill;
import com.melex.job.payload.JobSkillRequest;

import java.util.List;
import java.util.Set;

public interface JobSkillService {

    JobSkillResponse createSkill(JobSkillRequest req) throws Exception;

    List<JobSkillResponse> getAllSkills();

    JobSkillResponse getSkillById(Long id);

    JobSkillResponse updateSkill(Long id, JobSkillRequest req);

    void deleteSkill(Long id);

    Set<JobSkill> getSkillsByIds(Set<Long> ids);
}
