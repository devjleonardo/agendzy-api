package com.agendzy.api.entrypoint.http.resource.business.onboarding;

import com.agendzy.api.core.usecase.business.boundary.input.data.openinghours.BusinessOpeningHoursInput;
import com.agendzy.api.core.usecase.business.boundary.input.data.location.BusinessLocationInput;
import com.agendzy.api.core.usecase.business.boundary.input.data.profile.BusinessProfileInput;
import com.agendzy.api.core.usecase.business.boundary.input.data.service.ServiceInput;
import com.agendzy.api.core.usecase.business.interactor.onboarding.*;
import com.agendzy.api.entrypoint.http.HandlingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/businesses/{businessId}/onboarding")
@RequiredArgsConstructor
public class BusinessOnboardingResource {

    private final OnboardingAddServiceUseCase onboardingAddServiceUseCase;
    private final OnboardingAddOpeningHoursUseCase onboardingAddOpeningHoursUseCase;
    private final OnboardingAddLocationUseCase onboardingAddLocationUseCase;
    private final OnboardingAddProfilePhotoUseCase onboardingAddProfilePhotoUseCase;
    private final GetOnboardingStatusUseCase getOnboardingStatusUseCase;
    private final ActivateBusinessUseCase activateBusinessUseCase;

    @PostMapping("/services")
    public ResponseEntity<Object> addServices(@PathVariable String businessId,
                                              @RequestBody @Valid List<ServiceInput> requests) {
        return HandlingResponse.execute(onboardingAddServiceUseCase.execute(businessId, requests));
    }

    @PostMapping("/opening-hours")
    public ResponseEntity<Object> addOpeningHours(@PathVariable String businessId,
                                                  @RequestBody @Valid List<BusinessOpeningHoursInput> inputs) {
        return HandlingResponse.execute(onboardingAddOpeningHoursUseCase.execute(businessId,inputs));
    }

    @PostMapping("/location")
    public ResponseEntity<Object> addLocation(@PathVariable String businessId,
                                              @RequestBody @Valid BusinessLocationInput input) {
        return HandlingResponse.execute(onboardingAddLocationUseCase.execute(businessId, input));
    }

    @PostMapping("/profile")
    public ResponseEntity<Object> addProfilePhoto(@PathVariable String businessId,
                                                  @RequestBody @Valid BusinessProfileInput input) {
        return HandlingResponse.execute(onboardingAddProfilePhotoUseCase.execute(businessId, input));
    }

    @GetMapping("/status")
    public ResponseEntity<Object> getStatus(@PathVariable String businessId) {
        return HandlingResponse.execute(getOnboardingStatusUseCase.execute(businessId));
    }

    @PutMapping("/activate")
    public ResponseEntity<Object> activate(@PathVariable String businessId) {
        return HandlingResponse.execute(activateBusinessUseCase.execute(businessId));
    }

}
