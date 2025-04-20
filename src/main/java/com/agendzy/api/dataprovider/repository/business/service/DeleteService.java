package com.agendzy.api.dataprovider.repository.business.service;

import com.agendzy.api.core.domain.business.service.BusinessService;
import com.agendzy.api.core.gateway.common.DeleteGateway;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;

@Component
@RequiredArgsConstructor
public class DeleteService implements DeleteGateway<BusinessService> {

    private final ServiceRepository repository;

    @Override
    public OutputResponse<Void> execute(BusinessService businessService) {
        repository.delete(businessService);
        return success();
    }

}
