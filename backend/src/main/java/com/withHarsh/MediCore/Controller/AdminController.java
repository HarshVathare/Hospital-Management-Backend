package com.withHarsh.MediCore.Controller;

import com.withHarsh.MediCore.DTO.*;
import com.withHarsh.MediCore.Services.AdminServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminServices adminServices;

    @PostMapping("/docter")
    public ResponseEntity<CreateDocterResponceDTO> CreateDocter(@RequestBody CreateDocterRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminServices.createDocter(requestDTO));
    }

    @GetMapping("/docters")
    public ResponseEntity<List<PatientResponceDTO>> fetchAllDocters(
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String experienceInYears,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {

        if (specialization != null) {
            return ResponseEntity.ok(adminServices.getDocterBySpecialization(specialization, page, size));
        }

        if (experienceInYears != null) {
            return ResponseEntity.ok(adminServices.getDocterByExperience(experienceInYears, page, size));
        }

        return ResponseEntity.ok(adminServices.fetchAllDocters(page, size));
    }

    @DeleteMapping("/docter/{id}")
    public ResponseEntity<String> deleteDocterById(@PathVariable Long id) {
        return ResponseEntity.ok(adminServices.deleteDocterById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<RegisterResponceDTO>> fetchAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        return ResponseEntity.ok(adminServices.fetchAllUsers(page, size));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        return ResponseEntity.ok(adminServices.deleteUserById(id));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponseDTO> getDashboard() {
        return ResponseEntity.ok(adminServices.getDashboard());
    }



}
