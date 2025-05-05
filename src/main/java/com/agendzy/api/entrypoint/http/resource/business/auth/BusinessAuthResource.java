package com.agendzy.api.entrypoint.http.resource.business.auth;

import com.agendzy.api.core.usecase.business.boundary.input.data.auth.signin.CollaboratorAuthCredentialInput;
import com.agendzy.api.core.usecase.business.boundary.input.data.auth.signup.SignupBusinessInput;
import com.agendzy.api.core.usecase.business.interactor.auth.signin.AuthCollaboratorUseCase;
import com.agendzy.api.core.usecase.business.interactor.auth.signup.SignupBusinessUseCase;
import com.agendzy.api.entrypoint.http.HandlingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/businesses/auth")
@RequiredArgsConstructor
public class BusinessAuthResource {

    private final SignupBusinessUseCase signupBusinessUseCase;
    private final AuthCollaboratorUseCase authCollaboratorUseCase;

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signup(@RequestBody @Valid SignupBusinessInput input) {
        return HandlingResponse.execute(signupBusinessUseCase.execute(input));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Object> login(@RequestBody CollaboratorAuthCredentialInput input) {
        return ResponseEntity.status(201).body(authCollaboratorUseCase.execute(input));
    }

}
