package com.withHarsh.MediCore.DTO;

import com.withHarsh.MediCore.Entity.Docter;
import com.withHarsh.MediCore.Entity.Patient;
import com.withHarsh.MediCore.Entity.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    //id created // 1
    private String name;
    private String email;
    private String password;

//    private RoleType role; if PATIENT

    private String age;
    private String gender;
    private String medicalHistory;

}
