package com.scotersharing.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Phone is mandatory")
    private String phone;

    @Size(min = 6, message = "Password should be at least 6 characters")
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Min(value = 0, message = "Driving experience should be a non-negative number")
    private int drivingExperience;
}
