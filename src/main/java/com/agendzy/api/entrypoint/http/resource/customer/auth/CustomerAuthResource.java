package com.agendzy.api.entrypoint.http.resource.customer.auth;

import com.agendzy.api.core.usecase.customer.boundary.input.data.auth.CustomerAuthCredentialInput;
import com.agendzy.api.core.usecase.customer.interactor.auth.AuthCustomerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/customers/auth")
@RequiredArgsConstructor
public class CustomerAuthResource {

    private final AuthCustomerUseCase authCustomerUseCase;

    @PostMapping("/sign-in")
    public ResponseEntity<Object> doLogin(@RequestBody CustomerAuthCredentialInput input) {
        return ResponseEntity.status(201).body(authCustomerUseCase.execute(input));
    }

}
