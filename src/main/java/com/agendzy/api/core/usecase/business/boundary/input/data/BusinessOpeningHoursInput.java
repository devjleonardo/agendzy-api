package com.agendzy.api.core.usecase.business.boundary.input.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessOpeningHoursInput {

    private String dayOfWeek;

    private String startTime;

    private String endTime;

    private boolean enabled;
    
}
