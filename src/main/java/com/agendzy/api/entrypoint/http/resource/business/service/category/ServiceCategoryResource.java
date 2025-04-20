package com.agendzy.api.entrypoint.http.resource.business.service.category;

import com.agendzy.api.core.usecase.business.boundary.input.data.service.category.ServiceCategoryInput;
import com.agendzy.api.core.usecase.business.interactor.service.category.*;
import com.agendzy.api.entrypoint.http.HandlingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/businesses/{businessId}/services/categories")
@RequiredArgsConstructor
public class ServiceCategoryResource {

    private final ListServiceCategoriesUseCase listServiceCategoriesUseCase;
    private final GetServiceCategoryUseCase getServiceCategoryUseCase;
    private final CreateServiceCategoryUseCase createServiceCategoryUseCase;
    private final UpdateServiceCategoryUseCase updateServiceCategoryUseCase;
    private final DeleteServiceCategoryUseCase deleteServiceCategoryUseCase;

    @GetMapping
    public ResponseEntity<Object> listAllServiceCategories(@PathVariable String businessId) {
        return HandlingResponse.execute(listServiceCategoriesUseCase.execute(businessId));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Object> getServiceCategoryById(@PathVariable String businessId,
                                                         @PathVariable String categoryId) {
        return HandlingResponse.execute(getServiceCategoryUseCase.execute(businessId, categoryId));
    }

    @PostMapping
    public ResponseEntity<Object> createServiceCategory(@PathVariable String businessId,
                                                        @RequestBody @Valid ServiceCategoryInput request) {
        return HandlingResponse.execute(createServiceCategoryUseCase.execute(businessId, request));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Object> updateServiceCategory(@PathVariable String businessId,
                                                        @PathVariable String categoryId,
                                                        @RequestBody @Valid ServiceCategoryInput request) {
        return HandlingResponse.execute(updateServiceCategoryUseCase.execute(businessId, categoryId, request));
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteServiceCategoryById(@PathVariable String businessId,
                                                            @PathVariable String categoryId) {
        return HandlingResponse.execute(deleteServiceCategoryUseCase.execute(businessId, categoryId));
    }

}
