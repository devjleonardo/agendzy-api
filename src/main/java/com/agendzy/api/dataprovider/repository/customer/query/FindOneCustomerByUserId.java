package com.agendzy.api.dataprovider.repository.customer.query;

import com.agendzy.api.core.domain.customer.Customer;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereUserId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory;
import com.agendzy.api.dataprovider.repository.customer.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.agendzy.api.core.usecase.common.boundary.output.OutputError.entityNotFound;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;

@Component
@AllArgsConstructor
public class FindOneCustomerByUserId implements FindOneGateway<Customer, WhereUserId> {

    private final CustomerRepository repository;

    public OutputResponse<Customer> execute(WhereUserId where) {
        return repository
                .findByUserId(where.userId())
                .map(OutputResponseFactory::success)
                .orElseGet(() -> error(entityNotFound("Customer not found with userId: " + where.userId())));
    }

}
