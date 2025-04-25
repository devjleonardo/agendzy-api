package com.agendzy.api.core.usecase.business.boundary.input.data;

import com.agendzy.api.core.domain.business.BusinessLocation;
import com.agendzy.api.core.domain.business.BusinessSegment;
import com.agendzy.api.core.domain.business.BusinessTeamSize;
import com.agendzy.api.core.usecase.business.boundary.input.data.service.ServiceInput;
import com.agendzy.api.core.usecase.common.boundary.input.data.user.CreateUserInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CreateBusinessInput {

    @NotBlank(message = "Business name is required.")
    private String name;

    private BusinessSegment segment;

    private BusinessLocation location;

    @NotNull(message = "Team size is required.")
    private BusinessTeamSize teamSize;

    @NotNull(message = "User is required")
    private CreateUserInput user;

    private List<ServiceInput> services;

    private List<BusinessOpeningHoursInput> openingHours;

}
