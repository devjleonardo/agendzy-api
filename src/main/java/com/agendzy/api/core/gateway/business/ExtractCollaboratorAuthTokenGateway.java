package com.agendzy.api.core.gateway.business;

import com.agendzy.api.core.usecase.business.boundary.output.auth.AuthCollaboratorTokenDataOutput;

public interface ExtractCollaboratorAuthTokenGateway {

    AuthCollaboratorTokenDataOutput execute(String token);

}
