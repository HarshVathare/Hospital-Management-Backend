package com.withHarsh.MediCore.Controller;

import com.withHarsh.MediCore.DTO.ProfileRequestDTO;
import com.withHarsh.MediCore.DTO.ProfileResponceDTO;
import com.withHarsh.MediCore.Services.PatientServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientServices patientServices;

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponceDTO> getProfile(Authentication authentication) {
        return ResponseEntity.ok(patientServices.getProfile(authentication));
    }

    @PutMapping("/profile")
    public ResponseEntity<ProfileResponceDTO> UpdateProfile(@Valid @RequestBody ProfileRequestDTO profileRequestDTO, Authentication authentication) {
        return ResponseEntity.ok(patientServices.updateProfile(profileRequestDTO, authentication));
    }
}
