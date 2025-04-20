package com.agendzy.api.core.usecase.customer.interactor.auth;

import com.agendzy.api.core.domain.customer.Customer;
import com.agendzy.api.core.domain.customer.auth.CustomerContext;
import com.agendzy.api.core.domain.customer.auth.CustomerContextCurrent;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.gateway.customer.ExtractCustomerAuthTokenGateway;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereId;
import com.agendzy.api.core.usecase.common.boundary.output.data.DomainError;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.core.usecase.customer.boundary.output.data.auth.AuthCustomerTokenDataOutput;
import com.agendzy.api.util.LogUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.agendzy.api.core.usecase.common.boundary.output.OutputError.unauthorized;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;

@Service
@RequiredArgsConstructor
public class AuthCustomerFilterUseCase {

    private final ExtractCustomerAuthTokenGateway extractAuthToken;
    private final FindOneGateway<Customer, WhereId> findCustomerById;

    public OutputResponse<Customer> execute(String token) {
        if (!StringUtils.hasText(token)) {
            LogUtil.logWarn("Token ausente no cabeçalho Authorization.");
            return error(unauthorized(DomainError.NO_TOKEN));
        }

        AuthCustomerTokenDataOutput authTokenData;

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

        var customerResponse = findCustomerById.execute(new WhereId(authTokenData.getCustomerId()));

        if (customerResponse.isError()) {
            LogUtil.logWarn("Customer não encontrado para ID: " + authTokenData.getCustomerId());
            return error(unauthorized(DomainError.USER_NOT_FOUND_FOR_TOKEN));
        }

        CustomerContextCurrent.set(new CustomerContext(authTokenData));

        return success(customerResponse.getData());
    }

}
