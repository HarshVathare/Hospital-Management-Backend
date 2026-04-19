package com.withHarsh.MediCore.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponseDTO {

    private Long totalPatients;
    private Long totalDocters;
    private Long totalAppointments;
    private Long todayAppointments;

}
