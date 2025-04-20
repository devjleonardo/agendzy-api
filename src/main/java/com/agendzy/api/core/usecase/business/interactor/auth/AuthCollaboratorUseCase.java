package com.agendzy.api.core.usecase.business.interactor.auth;

import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.domain.business.collaborator.Collaborator;
import com.agendzy.api.core.domain.common.User;
import com.agendzy.api.core.exception.BusinessRuleException;
import com.agendzy.api.core.exception.UnauthorizedException;
import com.agendzy.api.core.gateway.business.CreateCollaboratorAuthGateway;
import com.agendzy.api.core.gateway.common.FindAllWithFilterGateway;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.gateway.common.auth.ValidatePasswordGateway;
import com.agendzy.api.core.usecase.business.boundary.input.data.auth.CollaboratorAuthCredentialInput;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereEmail;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereUserId;
import com.agendzy.api.core.usecase.common.boundary.output.data.DomainError;
import com.agendzy.api.util.LogUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthCollaboratorUseCase {

    private final FindOneGateway<User, WhereEmail> findUserByEmail;
    private final ValidatePasswordGateway validatePassword;
    private final CreateCollaboratorAuthGateway createCollaboratorAuth;

    private final FindAllWithFilterGateway<Collaborator, WhereUserId> findCollaboratorsByUserId;

    public Map<String, Object> execute(CollaboratorAuthCredentialInput input) {
        try {
            User user = authenticateUser(input);

            List<Collaborator> collaborators = findCollaboratorsByUserId
                .execute(new WhereUserId(user.getId()))
                .getData();

            if (collaborators.isEmpty()) {
                throw new UnauthorizedException(DomainError.COLLABORATOR_NOT_FOUND_BY_USER);
            }

            if (input.getBusinessId() != null) {
                return authenticateInSpecificBusiness(input.getBusinessId(), collaborators);
            }

            if (collaborators.size() == 1) {
                return handleSingleBusiness(collaborators.getFirst());
            }

            return buildBusinessListResponse(collaborators);
        } catch (Exception e) {
            LogUtil.logError("Erro durante autenticação de colaborador.", e);
            throw e;
        }
    }

    private User authenticateUser(CollaboratorAuthCredentialInput input) {
        var result = findUserByEmail.execute(new WhereEmail(input.getEmail()));

        if (result.isError() || !validatePassword.execute(input.getPassword(), result.getData().getPassword())) {
            throw new UnauthorizedException(DomainError.CREDENTIAL_DATA_IS_INVALID);
        }

        return result.getData();
    }

    private Map<String, Object> authenticateInSpecificBusiness(String businessId, List<Collaborator> collaborators) {
        Collaborator collaborator = collaborators.stream()
            .filter(c -> c.getBusiness().getId().equals(businessId))
            .findFirst()
            .orElseThrow(() -> new BusinessRuleException(DomainError.BUSINESS_NOT_FOUND_FOR_USER));

        return buildAuthResponse(collaborator);
    }

    private Map<String, Object> handleSingleBusiness(Collaborator collaborator) {
        return buildAuthResponse(collaborator);
    }

    private Map<String, Object> buildAuthResponse(Collaborator collaborator) {
        User user = collaborator.getUser();
        Business business = collaborator.getBusiness();

        String token = createCollaboratorAuth.execute(collaborator, user, business);

        return Map.of(
            "collaboratorId", collaborator.getId(),
            "userName", user.getFullName(),
            "email", user.getEmail(),
            "businessId", business.getId(),
            "businessName", business.getName(),
            "role", collaborator.getRole().name(),
            "token", token
        );
    }

    private Map<String, Object> buildBusinessListResponse(List<Collaborator> collaborators) {
        List<Map<String, String>> businessList = collaborators.stream()
            .map(collab -> Map.of(
                "businessId", collab.getBusiness().getId(),
                "businessName", collab.getBusiness().getName(),
                "role", collab.getRole().name()
            ))
            .toList();

        return Map.of("businesses", businessList);
    }

}
