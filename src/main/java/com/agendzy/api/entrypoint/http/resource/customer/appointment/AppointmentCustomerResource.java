package com.agendzy.api.entrypoint.http.resource.customer.appointment;

import com.agendzy.api.core.usecase.appointment.boundary.input.data.CreateAppointmentByCustomerInput;
import com.agendzy.api.core.usecase.appointment.interactor.customer.CreateAppointmentByCustomerUseCase;
import com.agendzy.api.entrypoint.http.HandlingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/customers/appointments")
@RequiredArgsConstructor
public class AppointmentCustomerResource {

    private final CreateAppointmentByCustomerUseCase createAppointmentByCustomerUseCase;

    @PostMapping
    public ResponseEntity<Object> createAppointmentByCustomer(@RequestBody CreateAppointmentByCustomerInput input) {
        return HandlingResponse.execute(createAppointmentByCustomerUseCase.execute(input));
    }

}
