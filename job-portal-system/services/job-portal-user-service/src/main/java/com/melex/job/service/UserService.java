package com.melex.job.service;

import com.melex.job.dto.response.UserResponse;
import com.melex.job.model.User;
import com.melex.job.payload.UpdateUserRequest;

import java.util.List;


public interface UserService {

    User getUserByEmail(String email);

    User getUserById(Long id) throws Exception;

    List<User>getAllUsers();

    UserResponse updateProfile(String email, UpdateUserRequest req);

    UserResponse suspendUser(Long id) throws Exception;
    UserResponse activateUser(Long id) throws Exception;
    UserResponse deleteUser(Long id) throws Exception;
}
