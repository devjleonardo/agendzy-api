package com.agendzy.api.core.gateway.customer;

import com.agendzy.api.core.domain.common.User;
import com.agendzy.api.core.domain.customer.Customer;

public interface CreateCustomerAuthGateway {

    String execute(Customer customer, User user);

}
