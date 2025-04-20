package com.agendzy.api.dataprovider.repository.appointment.query;

import com.agendzy.api.core.domain.appointment.Appointment;
import com.agendzy.api.core.gateway.common.FindAllWithFilterGateway;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessIdAndProfessionalIdAndDate;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.dataprovider.repository.appointment.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;

@Component
@RequiredArgsConstructor
public class FindAllAppointmentsByBusinessIdAndProfessionalIdAndDate
        implements FindAllWithFilterGateway<Appointment, WhereBusinessIdAndProfessionalIdAndDate> {

    private final AppointmentRepository repository;

    @Override
    public OutputResponse<List<Appointment>> execute(WhereBusinessIdAndProfessionalIdAndDate where) {
        LocalDateTime startOfDay = where.date().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<Appointment> appointments = repository.findByBusinessIdAndProfessionalIdAndDate(
            where.businessId(), where.professionalId(), startOfDay, endOfDay
        );

        return success(appointments);
    }

}
