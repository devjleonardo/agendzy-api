package com.agendzy.api.dataprovider.repository.appointment;

import com.agendzy.api.core.domain.appointment.Appointment;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;

@Component
@RequiredArgsConstructor
public class SaveAppointment implements SaveGateway<Appointment> {

    private final AppointmentRepository repository;

    @Override
    public OutputResponse<Appointment> execute(Appointment appointment) {
        return success(repository.save(appointment));
    }

}
