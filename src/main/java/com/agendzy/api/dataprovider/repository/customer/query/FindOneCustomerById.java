package com.agendzy.api.dataprovider.repository.customer.query;

import com.agendzy.api.core.domain.customer.Customer;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory;
import com.agendzy.api.dataprovider.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.agendzy.api.core.usecase.common.boundary.output.OutputError.entityNotFound;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;

@Component
@RequiredArgsConstructor
public class FindOneCustomerById implements FindOneGateway<Customer, WhereId> {

    private final CustomerRepository repository;

    @Override
    public OutputResponse<Customer> execute(WhereId where) {
        return repository.findById(where.id())
                .map(OutputResponseFactory::success)
                .orElseGet(() -> error(entityNotFound("Customer not found for the specified ID.")));
    }

}
