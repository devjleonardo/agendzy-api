package com.agendzy.api.core.usecase.customer.interactor;

import com.agendzy.api.core.domain.common.User;
import com.agendzy.api.core.domain.customer.Customer;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.usecase.common.boundary.input.data.user.CreateUserInput;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereEmail;
import com.agendzy.api.core.usecase.common.boundary.input.query.WherePhoneNumber;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.core.usecase.common.interactor.CreateUserUseCase;
import com.agendzy.api.core.usecase.customer.boundary.output.data.CustomerOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.agendzy.api.core.usecase.common.boundary.output.OutputError.businessRuleViolation;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;

@Service
@RequiredArgsConstructor
public class CreateCustomerUseCase {

    private final CreateUserUseCase createUser;
    private final SaveGateway<Customer> saveCustomer;
    private final FindOneGateway<User, WherePhoneNumber> findUserByPhoneNumber;
    private final FindOneGateway<User, WhereEmail> findUserByEmail;

    public OutputResponse<CustomerOutput> execute(CreateUserInput input) {
        if (isPhoneNumberInUse(input.getPhoneNumber())) {
            return error(businessRuleViolation("A user with this phone number already exists."));
        }

        if (isEmailInUse(input.getEmail())) {
            return error(businessRuleViolation("A user with this email already exists."));
        }

        User savedUser = createUser.execute(input);
        Customer savedCustomer = createAndSaveCustomer(savedUser);

        return success(CustomerOutput.of(savedCustomer));
    }

    private boolean isPhoneNumberInUse(String phoneNumber) {
        return phoneNumber != null &&
            findUserByPhoneNumber.execute(new WherePhoneNumber(phoneNumber)).isSuccess();
    }

    private boolean isEmailInUse(String email) {
        return email != null &&
            findUserByEmail.execute(new WhereEmail(email)).isSuccess();
    }

    private Customer createAndSaveCustomer(User savedUser) {
        Customer customer = new Customer();
        customer.setUser(savedUser);
        return saveCustomer.execute(customer).getData();
    }

}
