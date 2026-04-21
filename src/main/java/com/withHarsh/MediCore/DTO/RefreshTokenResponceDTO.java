package com.withHarsh.MediCore.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenResponceDTO {
    private String accessToken;
    private String refreshToken;
}