package com.withHarsh.MediCore.DTO;

import com.withHarsh.MediCore.Entity.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocterProfileResponceDTO {

    private Long id; //user_id
    private Long docter_id;
    private String name;
    private String email;
    private String specialization;
    private String experianceInYears;
    private boolean availibility_stutus;
    private RoleType role;
    private LocalDateTime createdAt;

}
