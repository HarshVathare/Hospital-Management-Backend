package com.withHarsh.MediCore.Services.ImplService;

import com.withHarsh.MediCore.DTO.DocterProfileResponceDTO;

import com.withHarsh.MediCore.Entity.Docter;

import com.withHarsh.MediCore.Entity.User;
import com.withHarsh.MediCore.Repository.DocterRepository;
import com.withHarsh.MediCore.Repository.UserRepository;
import com.withHarsh.MediCore.Services.DocterServices;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class DocterServiceImpl implements DocterServices {

    private final UserRepository userRepository;
    private final DocterRepository docterRepository;

    public DocterServiceImpl(UserRepository userRepository, DocterRepository docterRepository) {
        this.userRepository = userRepository;
        this.docterRepository = docterRepository;
    }


    @Override
    public DocterProfileResponceDTO getProfile(Authentication authentication) {

        String email = authentication.getName(); // ✅ BEST WAY

        System.out.println("Email: " + email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Docter docter = docterRepository.findByUser_Id(user.getId()); // ✅ FIXED

        if (docter == null) {
            throw new RuntimeException("Doctor not found");
        }

        return new DocterProfileResponceDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                docter.getSpecialization(),
                docter.getExperianceInYears(),
                docter.isAvailibility_stutus(),
                user.getRole(),
                user.getCreated_at()
        );
    }

}
