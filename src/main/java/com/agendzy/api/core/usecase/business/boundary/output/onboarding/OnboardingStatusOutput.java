package com.agendzy.api.core.usecase.business.boundary.output.onboarding;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OnboardingStatusOutput {

    private boolean hasAtLeastOneService;

    private boolean hasOpeningHours;

    private boolean hasLocation;

    private boolean hasProfilePhoto;

    private boolean isReady;

}
