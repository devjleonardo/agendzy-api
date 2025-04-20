package com.agendzy.api.dataprovider.repository.business.service.category;

import com.agendzy.api.core.domain.business.service.BusinessServiceCategory;
import com.agendzy.api.core.gateway.common.DeleteGateway;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;

@Component
@RequiredArgsConstructor
public class DeleteServiceCategory implements DeleteGateway<BusinessServiceCategory> {

    private final ServiceCategoryRepository repository;

    @Override
    public OutputResponse<Void> execute(BusinessServiceCategory businessServiceCategory) {
        repository.delete(businessServiceCategory);
        return success();
    }

}
