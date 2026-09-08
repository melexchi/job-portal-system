package com.melex.job.payload;

import com.melex.job.domain.UserRole;
import com.melex.job.domain.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignupRequest {

    @NotBlank(message = "Full Name is required")
    private String fullName;

    @Email(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private String phone;

    @NotNull(message = "Role is required")
    private UserRole role;


}
