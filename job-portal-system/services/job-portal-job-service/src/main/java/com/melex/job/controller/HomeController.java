package com.melex.job.controller;

import com.melex.job.domain.UserRole;
import com.melex.job.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    @GetMapping
    public ApiResponse home(){

        return new ApiResponse("Service for managing job postings, search and filtering"+ UserRole.ROLE_EMPLOYER, true);
    }
}
