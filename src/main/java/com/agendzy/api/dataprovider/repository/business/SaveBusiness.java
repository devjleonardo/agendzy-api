package com.agendzy.api.dataprovider.repository.business;

import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;

@Component
@RequiredArgsConstructor
public class SaveBusiness implements SaveGateway<Business> {

    private final BusinessRepository repository;

    @Override
    public OutputResponse<Business> execute(Business business) {
        return success(repository.save(business));
    }

}
