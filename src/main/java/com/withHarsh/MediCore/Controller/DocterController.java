package com.withHarsh.MediCore.Controller;

import com.withHarsh.MediCore.DTO.DocterProfileResponceDTO;
import com.withHarsh.MediCore.Services.DocterServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/docters")
@RequiredArgsConstructor
public class DocterController {

    private final DocterServices docterServices;

    @GetMapping("/profile")
    public ResponseEntity<DocterProfileResponceDTO> getProfile(Authentication authentication) {
        return ResponseEntity.ok(docterServices.getProfile(authentication));
    }




}
