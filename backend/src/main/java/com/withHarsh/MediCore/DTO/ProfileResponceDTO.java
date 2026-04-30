package com.withHarsh.MediCore.DTO;

import com.withHarsh.MediCore.Entity.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponceDTO {

    private Long id;
    private Long patient_Id;
    private String name;
    private String email;
    private String age;
    private String gender;
    private String medicalRecord;
    private RoleType role;
    private LocalDateTime createdAt;

}
