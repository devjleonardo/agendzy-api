package com.agendzy.api.core.usecase.customer.boundary.output.data.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthCustomerTokenDataOutput {

    private String customerId;
    private String email;

    private Boolean tokenValid;
    private Boolean tokenExpired;

    public static AuthCustomerTokenDataOutput empty() {
        return AuthCustomerTokenDataOutput.builder().build();
    }

}
