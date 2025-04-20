package com.agendzy.api.core.domain.customer.auth;

import com.agendzy.api.core.usecase.customer.boundary.output.data.auth.AuthCustomerTokenDataOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public final class CustomerContext {

    private final String customerId;
    private final String email;

    public CustomerContext(AuthCustomerTokenDataOutput data) {
        this.customerId = data.getCustomerId();
        this.email = data.getEmail();
    }

}
