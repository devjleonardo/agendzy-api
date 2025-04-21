package com.agendzy.api.entrypoint.http.resource.business.appointment;

import com.agendzy.api.core.usecase.appointment.boundary.input.data.CreateAppointmentByCollaboratorInput;
import com.agendzy.api.core.usecase.appointment.interactor.business.CreateAppointmentByCollaboratorUseCase;
import com.agendzy.api.entrypoint.http.HandlingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/business/{businessId}/appointments")
@RequiredArgsConstructor
public class AppointmentCollaboratorResource {

    private final CreateAppointmentByCollaboratorUseCase createAppointmentByCollaboratorUseCase;

    @PostMapping
    public ResponseEntity<Object> createAppointmentByCollaborator(@PathVariable String businessId,
                                                      @RequestBody @Valid CreateAppointmentByCollaboratorInput input) {
        return HandlingResponse.execute(createAppointmentByCollaboratorUseCase.execute(businessId, input));
    }

}
