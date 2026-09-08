package com.melex.job.controller;

import com.melex.job.domain.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


    @GetMapping
    public String home (){
        return "Welcome to the Job Portal Company Service!" + UserRole.ROLE_JOB_SEEKER;
    }
}
