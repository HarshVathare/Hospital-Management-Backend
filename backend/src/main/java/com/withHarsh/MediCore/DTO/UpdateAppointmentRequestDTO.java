package com.withHarsh.MediCore.DTO;

import com.withHarsh.MediCore.Entity.type.AppointType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAppointmentRequestDTO {

    private AppointType appointment_status;
    private boolean availability; // cleaner naming
}
