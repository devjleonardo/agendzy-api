package com.agendzy.api.entrypoint.http.resource.business.auth;

import com.agendzy.api.core.usecase.business.boundary.input.data.auth.CollaboratorAuthCredentialInput;
import com.agendzy.api.core.usecase.business.interactor.auth.AuthCollaboratorUseCase;
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

    private final AuthCollaboratorUseCase authCollaboratorUseCase;

    @PostMapping("/sign-in")
    public ResponseEntity<Object> doLogin(@RequestBody CollaboratorAuthCredentialInput input) {
        return ResponseEntity.status(201).body(authCollaboratorUseCase.execute(input));
    }

}
