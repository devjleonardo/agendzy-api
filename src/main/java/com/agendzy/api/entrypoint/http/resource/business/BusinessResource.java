package com.agendzy.api.entrypoint.http.resource.business;

import com.agendzy.api.core.usecase.business.boundary.input.data.CreateBusinessInput;
import com.agendzy.api.core.usecase.business.interactor.CreateBusinessUseCase;
import com.agendzy.api.entrypoint.http.HandlingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/businesses")
@RequiredArgsConstructor
public class BusinessResource {

    private final CreateBusinessUseCase createBusinessUseCase;

    @PostMapping
    public ResponseEntity<Object> createBusinessWithOwner(@RequestBody @Valid CreateBusinessInput input) {
        return HandlingResponse.execute(createBusinessUseCase.execute(input));
    }

}
