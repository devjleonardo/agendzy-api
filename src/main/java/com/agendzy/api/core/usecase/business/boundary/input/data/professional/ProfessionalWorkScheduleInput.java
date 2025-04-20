package com.agendzy.api.core.usecase.business.boundary.input.data.professional;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
public class ProfessionalWorkScheduleInput {

    private String id;

    @NotNull(message = "Day of week is required.")
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Start time is required.")
    private LocalTime startTime;

    @NotNull(message = "End time is required.")
    private LocalTime endTime;

    private boolean enabled = true;

}
