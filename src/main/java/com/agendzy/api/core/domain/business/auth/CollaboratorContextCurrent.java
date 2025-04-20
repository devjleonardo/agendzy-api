package com.agendzy.api.core.domain.business.auth;

import com.agendzy.api.core.domain.business.collaborator.CollaboratorRole;
import com.agendzy.api.core.usecase.business.boundary.output.auth.AuthCollaboratorTokenDataOutput;

public abstract class CollaboratorContextCurrent {

    private static final ThreadLocal<CollaboratorContext> CONTEXT = ThreadLocal.withInitial(() ->
        new CollaboratorContext(AuthCollaboratorTokenDataOutput.empty()));

    private CollaboratorContextCurrent() {}

    public static void set(CollaboratorContext context) {
        CONTEXT.set(context);
    }

    public static String getCollaboratorId() {
        return CONTEXT.get().getCollaboratorId();
    }

    public static String getEmail() {
        return CONTEXT.get().getEmail();
    }

    public static String getBusinessId() {
        return CONTEXT.get().getBusinessId();
    }

    public static CollaboratorRole getRole() {
        return CONTEXT.get().getRole();
    }

    public static void clear() {
        CONTEXT.remove();
    }

}
