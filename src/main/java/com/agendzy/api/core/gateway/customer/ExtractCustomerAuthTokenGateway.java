package com.agendzy.api.core.gateway.customer;

import com.agendzy.api.core.usecase.customer.boundary.output.data.auth.AuthCustomerTokenDataOutput;

public interface ExtractCustomerAuthTokenGateway {

    AuthCustomerTokenDataOutput execute(String token);

}
