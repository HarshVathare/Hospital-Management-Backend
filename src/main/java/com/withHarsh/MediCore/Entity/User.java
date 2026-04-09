package com.withHarsh.MediCore.Entity;

import com.withHarsh.MediCore.Entity.type.RoleType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType role = RoleType.PATIENT;

    @UpdateTimestamp
    private LocalDateTime updated_At;

    @CreationTimestamp
    private LocalDateTime created_at;
}
