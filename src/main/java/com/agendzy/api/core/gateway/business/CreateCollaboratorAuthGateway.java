package com.agendzy.api.core.gateway.business;

import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.domain.business.collaborator.Collaborator;
import com.agendzy.api.core.domain.common.User;

public interface CreateCollaboratorAuthGateway {

    String execute(Collaborator collaborator, User user, Business business);

}
