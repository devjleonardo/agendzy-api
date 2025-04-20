package com.agendzy.api.core.usecase.common.boundary.output.data;

import com.agendzy.api.core.usecase.common.boundary.output.OutputError;
import lombok.Getter;

@Getter
public enum DomainError implements OutputError {

    DEFAULT("An unexpected error occurred. Please try again."),
    USER_ALREADY_EXISTS("The email is already registered."),
    TOKEN_IS_INVALID("The provided token is invalid. Please provide a valid token."),
    TOKEN_EXPIRED("The token has expired. Please log in again to obtain a new token."),
    NO_TOKEN("No token was provided. Please include a valid token in your request."),
    CREDENTIAL_DATA_IS_INVALID("The provided credentials are invalid. Please check your email and password."),
    NO_ACCOUNTS_FOUND_USER("No accounts associated with the user."),
    USER_WORKSPACE_NOT_FOUND_BY_ID("UserWorkspace not found for the given ID"),
    USER_WORKSPACE_NOT_FOUND("UserWorkspace not found for given userId and workspaceId"),
    WORKSPACE_NOT_FOUND_BY_ID("Workspace not found for the given ID."),
    WORKSPACE_NOT_FOUND_BY_USER_ID("No workspace found for the given user ID."),
    ACCOUNT_NOT_FOUND("No account found."),
    INVITE_ALREADY_SENT("This email has already been invited to this workspace."),
    COLLABORATOR_NOT_FOUND_BY_USER("No collaborator found associated with this user."),
    BUSINESS_NOT_FOUND_FOR_USER("This business was not found or is not associated with the current user."),
    BUSINESS_ACCESS_DENIED("You do not have permission to access this business."),
    TOKEN_MALFORMED("The token format is invalid."),
    TOKEN_SIGNATURE_INVALID("The token signature is invalid."),
    USER_NOT_FOUND_FOR_TOKEN("No user found for the token information."),
    COLLABORATOR_ALREADY_EXISTS_IN_BUSINESS("A collaborator with this email already exists in this business.");

    private final String description;

    DomainError(String description) {
        this.description = description;
    }

    @Override
    public String getType() {
        return "";
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public String getDetail() {
        return description;
    }
}
