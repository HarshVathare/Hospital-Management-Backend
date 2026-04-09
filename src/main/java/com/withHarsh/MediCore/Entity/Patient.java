package com.withHarsh.MediCore.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_Id")
    private User user;

    @Column(nullable = false)
    private String age;

    @Column(nullable = false)
    private String gender;

    @Column(length = 3000)
    private String medicalHistory;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "patient", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<Appointment> appointment = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<Medical_Records> medicalRecords = new ArrayList<>();
}
