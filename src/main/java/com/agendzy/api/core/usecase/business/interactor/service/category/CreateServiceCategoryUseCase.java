package com.agendzy.api.core.usecase.business.interactor.service.category;

import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.domain.business.service.BusinessServiceCategory;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.usecase.business.boundary.input.data.service.category.ServiceCategoryInput;
import com.agendzy.api.core.usecase.business.boundary.output.service.category.ServiceCategoryOutput;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;
import static com.agendzy.api.core.validator.business.BusinessAccessValidator.checkAccessAndGetBusiness;

@Service
@RequiredArgsConstructor
public class CreateServiceCategoryUseCase {

    private final FindOneGateway<Business, WhereId> findBusinessById;
    private final SaveGateway<BusinessServiceCategory> saveCategory;

    public OutputResponse<ServiceCategoryOutput> execute(String businessId, ServiceCategoryInput input) {
        Business business = checkAccessAndGetBusiness(businessId, findBusinessById).getData();

        BusinessServiceCategory category = new BusinessServiceCategory();
        category.setName(input.getName());
        category.setActive(input.isActive());
        category.setBusiness(business);

        var savedCategory = saveCategory.execute(category);

        return success(ServiceCategoryOutput.of(savedCategory.getData()));
    }

}
