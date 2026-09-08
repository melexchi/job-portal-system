package com.melex.job.service;

import com.melex.job.dto.JobRequest;
import com.melex.job.dto.JobResponse;
import com.melex.job.payload.JobSearchRequest;

import java.util.List;

public interface JobService {

    JobResponse createJob(Long employerId, JobRequest req);

    JobResponse getJobById(Long id) throws Exception;

    List<JobResponse> getJobs(JobSearchRequest request);

    List<JobResponse> getJobsByCompany(Long companyId);

    JobResponse updateJob(Long jobId,Long employerId, JobRequest req) throws Exception;

    JobResponse publishJob(Long jobId,Long employerId ) throws Exception;

    JobResponse closeJob(Long jobId,Long employerId ) throws Exception;

    void deleteJob(Long jobId,Long employerId ) throws Exception;


    List<JobResponse> getAllJobsAdmin();

}
