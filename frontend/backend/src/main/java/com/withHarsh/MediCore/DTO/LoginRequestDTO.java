package com.withHarsh.MediCore.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @Schema(example = "user@gmail.com")
    @Email(message = "Email is required ..!")
    private String email;

    @Schema(example = "password123")
    @NotBlank(message = "Password is required ..!")
    private String password;
}
