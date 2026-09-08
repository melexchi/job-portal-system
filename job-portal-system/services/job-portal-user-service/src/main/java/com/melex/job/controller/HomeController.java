package com.melex.job.controller;

import com.melex.job.domain.UserRole;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String home(){
        return "job-portal-user-service is up and running "+ UserRole.ROLE_JOB_SEEKER;
    }
}
