package com.agendzy.api.core.usecase.business.interactor.service.category;

import com.agendzy.api.core.domain.business.service.BusinessServiceCategory;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessIdAndEntityId;
import com.agendzy.api.core.usecase.business.boundary.output.service.category.ServiceCategoryOutput;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;
import static com.agendzy.api.core.validator.business.BusinessAccessValidator.checkAccessToBusiness;

@Service
@RequiredArgsConstructor
public class GetServiceCategoryUseCase {

    private final FindOneGateway<BusinessServiceCategory, WhereBusinessIdAndEntityId> findCategoryByBusinessAndId;

    public OutputResponse<ServiceCategoryOutput> execute(String businessId, String categoryId) {
        checkAccessToBusiness(businessId);

        var categoryResponse = findCategoryByBusinessAndId.execute(
                new WhereBusinessIdAndEntityId(businessId, categoryId)
        );

        if (categoryResponse.isError()) {
            return error(categoryResponse.getErrors());
        }

        return success(ServiceCategoryOutput.of(categoryResponse.getData()));
    }

}
