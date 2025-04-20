package com.agendzy.api.entrypoint.http.resource.business.service;

import com.agendzy.api.core.usecase.business.boundary.input.data.service.ServiceInput;
import com.agendzy.api.core.usecase.business.interactor.service.*;
import com.agendzy.api.entrypoint.http.HandlingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/businesses/{businessId}/services")
@RequiredArgsConstructor
public class ServiceResource {

    private final ListServicesUseCase listServicesUseCase;
    private final GetServiceUseCase getServiceUseCase;
    private final CreateServiceUseCase createServiceUseCase;
    private final UpdateServiceUseCase updateServiceUseCase;
    private final DeleteServiceUseCase deleteServiceUseCase;

    @GetMapping
    public ResponseEntity<Object> listAllServices(@PathVariable String businessId) {
        return HandlingResponse.execute(listServicesUseCase.execute(businessId));
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<Object> getServiceById(@PathVariable String businessId,
                                                         @PathVariable String serviceId) {
        return HandlingResponse.execute(getServiceUseCase.execute(businessId, serviceId));
    }

    @PostMapping
    public ResponseEntity<Object> createService(@PathVariable String businessId,
                                                @RequestBody @Valid ServiceInput request) {
        return HandlingResponse.execute(createServiceUseCase.execute(businessId, request));
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<Object> updateService(@PathVariable String businessId,
                                                @PathVariable String serviceId,
                                                @RequestBody @Valid ServiceInput request) {
        return HandlingResponse.execute(updateServiceUseCase.execute(businessId, serviceId, request));
    }

    @DeleteMapping("/{serviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteServiceById(@PathVariable String businessId,
                                                    @PathVariable String serviceId) {
        return HandlingResponse.execute(deleteServiceUseCase.execute(businessId, serviceId));
    }

}
