package com.agendzy.api.dataprovider.repository.customer;

import com.agendzy.api.core.domain.customer.Customer;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;

@Component
@RequiredArgsConstructor
public class SaveCustomer implements SaveGateway<Customer> {

    private final CustomerRepository repository;

    @Override
    public OutputResponse<Customer> execute(Customer customer) {
        return success(repository.save(customer));
    }

}
