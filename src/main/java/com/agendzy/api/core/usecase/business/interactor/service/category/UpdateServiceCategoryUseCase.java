package com.agendzy.api.core.usecase.business.interactor.service.category;

import com.agendzy.api.core.domain.business.service.BusinessServiceCategory;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.mapper.business.ServiceCategoryMapper;
import com.agendzy.api.core.usecase.business.boundary.input.data.service.category.ServiceCategoryInput;
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
public class UpdateServiceCategoryUseCase {

    private final FindOneGateway<BusinessServiceCategory, WhereBusinessIdAndEntityId> findServiceCategoryByBusinessAndId;
    private final SaveGateway<BusinessServiceCategory> saveServiceCategory;
    private final ServiceCategoryMapper serviceCategoryMapper;

    public OutputResponse<ServiceCategoryOutput> execute(String businessId,
                                                         String categoryId,
                                                         ServiceCategoryInput input) {
        checkAccessToBusiness(businessId);

        var serviceCategoryResponse = findServiceCategoryByBusinessAndId.execute(
            new WhereBusinessIdAndEntityId(businessId, categoryId)
        );

        if (serviceCategoryResponse.isError()) {
            return error(serviceCategoryResponse.getErrors());
        }

        BusinessServiceCategory serviceCategory = serviceCategoryResponse.getData();
        serviceCategoryMapper.updateServiceCategory(serviceCategory, input);

        var savedServiceCategory = saveServiceCategory.execute(serviceCategory);

        return success(ServiceCategoryOutput.of(savedServiceCategory.getData()));
    }

}
