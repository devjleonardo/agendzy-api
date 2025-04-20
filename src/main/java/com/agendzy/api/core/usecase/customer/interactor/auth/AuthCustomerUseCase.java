package com.agendzy.api.core.usecase.customer.interactor.auth;

import com.agendzy.api.core.domain.common.User;
import com.agendzy.api.core.domain.customer.Customer;
import com.agendzy.api.core.exception.UnauthorizedException;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.gateway.common.auth.ValidatePasswordGateway;
import com.agendzy.api.core.gateway.customer.CreateCustomerAuthGateway;
import com.agendzy.api.core.usecase.business.boundary.input.data.auth.CollaboratorAuthCredentialInput;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereEmail;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereUserId;
import com.agendzy.api.core.usecase.common.boundary.output.data.DomainError;
import com.agendzy.api.core.usecase.customer.boundary.input.data.auth.CustomerAuthCredentialInput;
import com.agendzy.api.util.LogUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthCustomerUseCase {

    private final FindOneGateway<Customer, WhereUserId> findCustomerByUserId;
    private final FindOneGateway<User, WhereEmail> findUserByEmail;
    private final ValidatePasswordGateway validatePassword;
    private final CreateCustomerAuthGateway createCustomerAuth;

    public Map<String, Object> execute(CustomerAuthCredentialInput input) {
        try {
            User user = authenticateUser(input);

            Customer customer = findCustomerByUserId
                .execute(new WhereUserId(user.getId()))
                .getData();

            if (customer == null) {
                throw new UnauthorizedException(DomainError.COLLABORATOR_NOT_FOUND_BY_USER);
            }

            return handleSingleBusiness(customer);

        } catch (Exception e) {
            LogUtil.logError("Erro durante autenticação do cliente.", e);
            throw e;
        }
    }

    private User authenticateUser(CustomerAuthCredentialInput input) {
        var result = findUserByEmail.execute(new WhereEmail(input.getEmail()));

        if (result.isError() || !validatePassword.execute(input.getPassword(), result.getData().getPassword())) {
            throw new UnauthorizedException(DomainError.CREDENTIAL_DATA_IS_INVALID);
        }

        return result.getData();
    }

    private Map<String, Object> handleSingleBusiness(Customer customer) {
        return buildAuthResponse(customer);
    }

    private Map<String, Object> buildAuthResponse(Customer customer) {
        User user = customer.getUser();

        String token = createCustomerAuth.execute(customer, user);

        return Map.of(
            "customerId", customer.getId(),
            "userName", user.getFullName(),
            "email", user.getEmail(),
            "token", token
        );
    }

}
