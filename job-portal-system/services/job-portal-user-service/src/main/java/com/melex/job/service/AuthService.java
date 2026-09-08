package com.melex.job.service;

import com.melex.job.payload.AuthResponse;
import com.melex.job.payload.LoginRequest;
import com.melex.job.payload.SignupRequest;

public interface AuthService {

    AuthResponse signup(SignupRequest req) throws Exception;

    AuthResponse login(LoginRequest req) throws Exception;
}
