package com.melex.job.controller;

import com.melex.job.dto.response.UserResponse;
import com.melex.job.mapper.UserMapper;
import com.melex.job.model.User;
import com.melex.job.payload.UpdateUserRequest;
import com.melex.job.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile(@RequestHeader("X-User-Email") String email) throws Exception{

        User user= userService.getUserByEmail(email);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }


    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateProfile(@RequestHeader("X-User-Email") String email, @RequestBody UpdateUserRequest req) throws Exception{

        return ResponseEntity.ok(userService.updateProfile(email,req));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse>getUserById(@PathVariable Long userId) throws Exception{
        User user= userService.getUserById(userId);

        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>>getAllUser() throws Exception{
        return ResponseEntity.ok(UserMapper.toDTOList(userService.getAllUsers()));
    }

    @PatchMapping("/{userId}/suspend")
    public ResponseEntity<UserResponse> suspendUser(@PathVariable Long userId) throws Exception{
        return ResponseEntity.ok(userService.suspendUser(userId));
    }

    @PatchMapping("/{userId}/activate")
    public ResponseEntity<UserResponse> activateUser(@PathVariable Long userId) throws Exception{

        return ResponseEntity.ok(userService.activateUser(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponse>deleteUser(@PathVariable Long userId)throws Exception{

        return ResponseEntity.ok(userService.deleteUser(userId));

    }
}
