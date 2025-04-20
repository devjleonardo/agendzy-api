package com.agendzy.api.entrypoint.http.resource.business.collaborator;

import com.agendzy.api.core.usecase.business.boundary.input.data.collaborator.CollaboratorInput;
import com.agendzy.api.core.usecase.business.interactor.collaborator.*;
import com.agendzy.api.entrypoint.http.HandlingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/businesses/{businessId}/collaborators")
@RequiredArgsConstructor
public class CollaboratorResource {

    private final ListCollaboratorsUseCase listCollaboratorsUseCase;
    private final GetCollaboratorUseCase getCollaboratorUseCase;
    private final CreateCollaboratorUseCase createCollaboratorUseCase;
    private final UpdateCollaboratorUseCase updateCollaboratorUseCase;
    private final DeleteCollaboratorUseCase deleteCollaboratorUseCase;

    @GetMapping
    public ResponseEntity<Object> listAllCollaborators(@PathVariable String businessId) {
        return HandlingResponse.execute(listCollaboratorsUseCase.execute(businessId));
    }

    @GetMapping("/{collaboratorId}")
    public ResponseEntity<Object> getCollaboratorById(@PathVariable String businessId,
                                                      @PathVariable String collaboratorId) {
        return HandlingResponse.execute(getCollaboratorUseCase.execute(businessId, collaboratorId));
    }

    @PostMapping
    public ResponseEntity<Object> createCollaborator(@PathVariable String businessId,
                                                     @RequestBody @Valid CollaboratorInput request) {
        return HandlingResponse.execute(createCollaboratorUseCase.execute(businessId, request));
    }

    @PutMapping("/{collaboratorId}")
    public ResponseEntity<Object> updateCollaborator(@PathVariable String businessId,
                                                     @PathVariable String collaboratorId,
                                                     @RequestBody @Valid CollaboratorInput request) {
        return HandlingResponse.execute(updateCollaboratorUseCase.execute(businessId, collaboratorId, request));
    }

    @DeleteMapping("/{collaboratorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteServiceById(@PathVariable String businessId,
                                                    @PathVariable String collaboratorId) {
        return HandlingResponse.execute(deleteCollaboratorUseCase.execute(businessId, collaboratorId));
    }

}
