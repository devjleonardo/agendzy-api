package com.agendzy.api.core.usecase.appointment.boundary.input.data;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateAppointmentByCollaboratorInput {

    @NotNull
    private String professionalId;

    @NotNull
    private String serviceId;

    private String customerId; // opcional (agendamento anônimo)

    private String customerName; // obrigatório se customerId == null

    private String customerPhone;

    @NotNull
    @Future
    private LocalDateTime startTime;

    private String notes;

    private String customerNotes;

}
