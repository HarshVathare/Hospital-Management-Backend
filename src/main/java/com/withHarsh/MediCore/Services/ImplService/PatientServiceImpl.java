package com.withHarsh.MediCore.Services.ImplService;

import com.withHarsh.MediCore.DTO.ProfileRequestDTO;
import com.withHarsh.MediCore.DTO.ProfileResponceDTO;
import com.withHarsh.MediCore.Entity.Patient;
import com.withHarsh.MediCore.Entity.User;
import com.withHarsh.MediCore.Repository.PatientRepository;
import com.withHarsh.MediCore.Repository.UserRepository;
import com.withHarsh.MediCore.Services.PatientServices;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientServices {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    @Override
    public ProfileResponceDTO getProfile(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Patient patient = patientRepository.findByUser(user);

        if (patient == null) {
            throw new RuntimeException("Patient not found");
        }

        return new ProfileResponceDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                patient.getAge(),
                patient.getGender(),
                patient.getMedicalHistory(),
                user.getRole(),
                user.getCreated_at()
        );
    }

    @Override
    public ProfileResponceDTO updateProfile(ProfileRequestDTO profileRequestDTO, Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // ✅ Update user
        user.setName(profileRequestDTO.getName());
        user.setEmail(profileRequestDTO.getEmail());

        // ✅ Fetch existing patient
        Patient patient = patientRepository.findByUser(user);

        if (patient == null) {
            throw new RuntimeException("Patient not found");
        }

        // ✅ Update patient fields
        patient.setAge(profileRequestDTO.getAge());
        patient.setGender(profileRequestDTO.getGender());
        patient.setMedicalHistory(profileRequestDTO.getMedicalRecord());

        // ✅ Save (cascade will handle patient)
        userRepository.save(user);

        return new ProfileResponceDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                patient.getAge(),
                patient.getGender(),
                patient.getMedicalHistory(),
                user.getRole(),
                user.getCreated_at()
        );
    }
}
