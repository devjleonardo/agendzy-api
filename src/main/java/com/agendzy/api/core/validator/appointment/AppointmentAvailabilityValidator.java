package com.agendzy.api.core.validator.appointment;

import com.agendzy.api.core.domain.appointment.Appointment;
import com.agendzy.api.core.domain.business.professional.Professional;
import com.agendzy.api.core.gateway.common.FindAllWithFilterGateway;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessIdAndProfessionalIdAndDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AppointmentAvailabilityValidator {

    private final FindAllWithFilterGateway<Appointment, WhereBusinessIdAndProfessionalIdAndDate>
        findAppointmentsByBusinessIdAndProfessionaIdAndDate;

    public boolean isAvailable(String businessId, Professional professional, LocalDateTime start, LocalDateTime end) {
        var filter = new WhereBusinessIdAndProfessionalIdAndDate(businessId, professional.getId(), start.toLocalDate());

        List<Appointment> existingAppointments = findAppointmentsByBusinessIdAndProfessionaIdAndDate
                .execute(filter)
                .getData();

        return existingAppointments.stream().noneMatch(app ->
            !(app.getEndTime().isBefore(start) || app.getStartTime().isAfter(end))
        );
    }


}
