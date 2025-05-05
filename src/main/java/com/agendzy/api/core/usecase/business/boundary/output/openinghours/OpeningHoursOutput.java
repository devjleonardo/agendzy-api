package com.agendzy.api.core.usecase.business.boundary.output.openinghours;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpeningHoursOutput {

    private String id;

    private String dayOfWeek;

    private String startTime;

    private String endTime;

    private boolean enabled;

}
