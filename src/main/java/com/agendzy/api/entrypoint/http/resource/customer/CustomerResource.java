package com.agendzy.api.entrypoint.http.resource.customer;

import com.agendzy.api.core.usecase.common.boundary.input.data.user.CreateUserInput;
import com.agendzy.api.core.usecase.customer.boundary.input.data.CreateCustomerInput;
import com.agendzy.api.core.usecase.customer.interactor.CreateCustomerUseCase;
import com.agendzy.api.entrypoint.http.HandlingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/customers")
@RequiredArgsConstructor
public class CustomerResource {

    private final CreateCustomerUseCase createCustomerUseCase;

    @PostMapping
    public ResponseEntity<Object> createCustomer(@RequestBody @Valid CreateUserInput input) {
        return HandlingResponse.execute(createCustomerUseCase.execute(input));
    }

}
