package com.agendzy.api.dataprovider.repository.business.service.query;

import com.agendzy.api.core.domain.business.service.BusinessService;
import com.agendzy.api.core.gateway.common.FindAllWithFilterGateway;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.dataprovider.repository.business.service.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;

@Component
@RequiredArgsConstructor
public class FindAllServicesByBusinessId implements FindAllWithFilterGateway<BusinessService, WhereBusinessId> {

    private final ServiceRepository repository;

    @Override
    public OutputResponse<List<BusinessService>> execute(WhereBusinessId where) {
        List<BusinessService> services = repository.findAllByBusinessId(where.businessId());
        return success(services);
    }

}
