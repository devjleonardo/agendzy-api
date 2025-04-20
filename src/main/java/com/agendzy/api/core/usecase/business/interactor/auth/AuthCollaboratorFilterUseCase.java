package com.agendzy.api.core.usecase.business.interactor.auth;

import com.agendzy.api.core.domain.business.auth.CollaboratorContext;
import com.agendzy.api.core.domain.business.auth.CollaboratorContextCurrent;
import com.agendzy.api.core.domain.business.collaborator.Collaborator;
import com.agendzy.api.core.gateway.business.ExtractCollaboratorAuthTokenGateway;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.usecase.business.boundary.output.auth.AuthCollaboratorTokenDataOutput;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereId;
import com.agendzy.api.core.usecase.common.boundary.output.data.DomainError;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.util.LogUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.agendzy.api.core.usecase.common.boundary.output.OutputError.unauthorized;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;

@Service
@RequiredArgsConstructor
public class AuthCollaboratorFilterUseCase {

    private final ExtractCollaboratorAuthTokenGateway extractAuthToken;
    private final FindOneGateway<Collaborator, WhereId> findCollaboratorById;

    public OutputResponse<Collaborator> execute(String token) {
        if (!StringUtils.hasText(token)) {
            LogUtil.logWarn("Token ausente no cabeçalho Authorization.");
            return error(unauthorized(DomainError.NO_TOKEN));
        }

        AuthCollaboratorTokenDataOutput authTokenData;

        try {
            authTokenData = extractAuthToken.execute(token);
        } catch (Exception e) {
            LogUtil.logError("Erro inesperado ao extrair token.", e);
            return error(unauthorized(DomainError.TOKEN_MALFORMED));
        }

        if (Boolean.FALSE.equals(authTokenData.getTokenValid())) {
            LogUtil.logWarn("Token JWT com assinatura inválida.");
            return error(unauthorized(DomainError.TOKEN_SIGNATURE_INVALID));
        }

        if (Boolean.TRUE.equals(authTokenData.getTokenExpired())) {
            LogUtil.logWarn("Token expirado.");
            return error(unauthorized(DomainError.TOKEN_EXPIRED));
        }

        var collaboratorResponse = findCollaboratorById.execute(new WhereId(authTokenData.getCollaboratorId()));

        if (collaboratorResponse.isError()) {
            LogUtil.logWarn("Colaborador não encontrado para ID: " + authTokenData.getCollaboratorId());
            return error(unauthorized(DomainError.USER_NOT_FOUND_FOR_TOKEN));
        }

        CollaboratorContextCurrent.set(new CollaboratorContext(authTokenData));

        return success(collaboratorResponse.getData());
    }

}
