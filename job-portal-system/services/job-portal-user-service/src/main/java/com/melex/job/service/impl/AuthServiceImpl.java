package com.melex.job.service.impl;

import com.melex.job.domain.UserRole;
import com.melex.job.domain.UserStatus;
import com.melex.job.mapper.UserMapper;
import com.melex.job.model.User;
import com.melex.job.payload.AuthResponse;
import com.melex.job.payload.LoginRequest;
import com.melex.job.payload.SignupRequest;
import com.melex.job.repository.UserRepository;
import com.melex.job.security.CustomUserDetailsService;
import com.melex.job.security.JwtProvider;
import com.melex.job.service.AuthService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService  customUserDetailsService;

    @Override
    public AuthResponse signup(SignupRequest req) throws Exception {

     if(userRepository.existsByEmail(req.getEmail())){
         throw new Exception("Email already exists:" + req.getEmail());
     }

     if(req.getRole()== UserRole.ROLE_ADMIN){
         throw new Exception("Admin role is not allowed for signup");
     }

        User user = User.builder()
                .fullName(req.getFullName())
                .email(req.getEmail())
                .phone(req.getPhone())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(req.getRole())
                .status(UserStatus.ACTIVE)
                .lastLogin(LocalDateTime.now())
                .build();

     User savedUser = userRepository.save(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication, savedUser.getId());

     AuthResponse res =new AuthResponse();
             res.setTitle("Welcome" + savedUser.getFullName());
           res.setMessage("User registered successfully");
           res.setJwt(jwt);
           res.setUser(UserMapper.toDTO(savedUser));

           return res;

    }

    @Override
    public AuthResponse login(LoginRequest req) throws Exception {
        
        Authentication authentication = authenticate(req.getEmail(), req.getPassword());

        User user = userRepository.findByEmail(req.getEmail());

        String jwt = jwtProvider.generateToken(authentication, user.getId());

        user.setLastLogin(LocalDateTime.now());

        userRepository.save(user);

        AuthResponse res = new AuthResponse();

        res.setTitle("Welcome back" + user.getFullName());
        res.setMessage("User logged in successfully");
        res.setJwt(jwt);
        res.setUser(UserMapper.toDTO(user));

        return res;
    }

    private Authentication authenticate(@Email(message = "Provide a Valid Email") @NotBlank(message = "Email is required") String email, @NotBlank(message = "Password is required") String password) throws Exception {


        UserDetails userDetails;
        try {
            userDetails = customUserDetailsService.loadUserByUsername(email);
        } catch (UsernameNotFoundException e) {

            throw new Exception("User not found with email: " + email);
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new Exception("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
