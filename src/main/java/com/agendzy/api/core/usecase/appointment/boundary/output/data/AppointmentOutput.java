package com.agendzy.api.core.usecase.appointment.boundary.output.data;

import com.agendzy.api.core.domain.appointment.Appointment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AppointmentOutput {

    private String id;
    private String professionalId;
    private String serviceId;
    private String customerId;
    private String customerName;
    private String customerPhone;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String notes;
    private String customerNotes;
    private String status;

    public static AppointmentOutput of(Appointment appointment) {
        return AppointmentOutput.builder()
                .id(appointment.getId())
                .professionalId(appointment.getProfessional().getId())
                .serviceId(appointment.getService().getId())
                .customerId(appointment.getCustomer() != null ? appointment.getCustomer().getId() : null)
                .customerName(appointment.getCustomerName())
                .customerPhone(appointment.getCustomerPhone())
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .notes(appointment.getNotes())
                .customerNotes(appointment.getCustomerNotes())
                .status(appointment.getStatus().name())
                .build();
    }
}
