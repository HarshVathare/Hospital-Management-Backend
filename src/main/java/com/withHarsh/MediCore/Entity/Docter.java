package com.withHarsh.MediCore.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Docter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_Id")
    private User user;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private String experianceInYears;

    @Column(nullable = false)
    private boolean availibility_stutus; //true or false

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "docter")
    private List<Appointment> appointment = new ArrayList<>();

    @OneToMany(mappedBy = "docter")
    private List<Medical_Records> medicalRecords = new ArrayList<>();
}
