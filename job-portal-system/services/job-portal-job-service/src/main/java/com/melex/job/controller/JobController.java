package com.melex.job.controller;

import com.melex.job.dto.ApiResponse;
import com.melex.job.dto.JobRequest;
import com.melex.job.dto.JobResponse;
import com.melex.job.payload.JobSearchRequest;
import com.melex.job.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {


    private final JobService jobService;


    @PostMapping("/create")
    public ResponseEntity<JobResponse> createJob(@RequestHeader("X-User-Id") Long employerId,
            @RequestBody @Valid JobRequest jobRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(employerId,jobRequest));
    }




    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long jobId) throws Exception {

        return ResponseEntity.ok(jobService.getJobById(jobId));

    }


    @GetMapping
    public ResponseEntity<List<JobResponse>> getJobs( @ModelAttribute JobSearchRequest req){

        return ResponseEntity.ok(jobService.getJobs(req));

    }


    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobResponse>> getJobsByCompany(@PathVariable Long companyId){

        return ResponseEntity.ok(jobService.getJobsByCompany(companyId));
    }

    @GetMapping("/admin")
    public ResponseEntity<List<JobResponse>>getAllJobsAdmin(){
        return ResponseEntity.ok(jobService.getAllJobsAdmin());
    }



    @PutMapping("/{id}")
    public ResponseEntity<JobResponse>updateJob(@PathVariable Long id, @RequestHeader("X-User_id") Long employerId, @RequestBody @Valid JobRequest req) throws Exception {

        return ResponseEntity.ok(jobService.updateJob(id,employerId,req));
    }


    @PatchMapping("/{id}/publish")
    public ResponseEntity<JobResponse> publishJob(@PathVariable Long id, @RequestHeader("X-User-id") Long employerId) throws Exception {

        return ResponseEntity.ok(jobService.publishJob(id, employerId));
    }


    @PatchMapping("/{id}/close")
    public ResponseEntity<JobResponse> closeJob(@PathVariable Long id, @RequestHeader("X-User-id") Long employerId) throws Exception {

        return ResponseEntity.ok(jobService.closeJob(id,employerId));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteJob(@PathVariable Long id, @RequestHeader("X-User-Id") Long employerId) throws Exception {

        jobService.deleteJob(id, employerId);

        return ResponseEntity.ok(new ApiResponse("Job Deleted Successfully", true));
    }


}
