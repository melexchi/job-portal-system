package com.melex.job.service.impl;

import com.melex.job.domain.UserStatus;
import com.melex.job.dto.response.UserResponse;
import com.melex.job.mapper.UserMapper;
import com.melex.job.model.User;
import com.melex.job.payload.UpdateUserRequest;
import com.melex.job.repository.UserRepository;
import com.melex.job.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if(user == null) throw new RuntimeException("User not found with email: " + email);
        return user;
    }

    @Override
    public User getUserById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(()-> new Exception("User not found with id: " ));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserResponse updateProfile(String email, UpdateUserRequest req) {

        User user= getUserByEmail(email);

        if(req.getFullName() != null) user.setFullName(req.getFullName());
        if(req.getPhone() != null) user.setPhone(req.getPhone());
        if(req.getProfileImage() != null) user.setProfileImage(req.getProfileImage());

        return UserMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserResponse suspendUser(Long id) throws Exception {

        User user =getUserById(id);
        user.setStatus(UserStatus.SUSPENDED);
        user.setSuspendedAt(LocalDateTime.now());

        return UserMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserResponse activateUser(Long id) throws Exception {

        User user= getUserById(id);
        user.setStatus(UserStatus.ACTIVE);
        user.setSuspendedAt(null);


        return UserMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserResponse deleteUser(Long id) throws Exception {

        User user = getUserById(id);
        user.setStatus(UserStatus.DELETED);
        user.setDeletedAt(LocalDateTime.now());
        return  UserMapper.toDTO(userRepository.save(user));
    }
}
