package com.agendzy.api.core.usecase.business.interactor.service;

import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.domain.business.service.BusinessService;
import com.agendzy.api.core.domain.business.service.BusinessServiceCategory;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.mapper.business.ServiceMapper;
import com.agendzy.api.core.usecase.business.boundary.input.data.service.ServiceInput;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessIdAndEntityId;
import com.agendzy.api.core.usecase.business.boundary.output.service.ServiceOutput;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;
import static com.agendzy.api.core.validator.business.BusinessAccessValidator.checkAccessAndGetBusiness;

@Service
@RequiredArgsConstructor
public class CreateServiceUseCase {

    private final FindOneGateway<Business, WhereId> findBusinessById;
    private final FindOneGateway<BusinessServiceCategory, WhereBusinessIdAndEntityId> findCategoryById;
    private final SaveGateway<BusinessService> saveService;
    private final ServiceMapper serviceMapper;

    public OutputResponse<ServiceOutput> execute(String businessId, ServiceInput input) {
        Business business = checkAccessAndGetBusiness(businessId, findBusinessById).getData();

        BusinessService service = new BusinessService();
        service.setBusiness(business);

        var categoryResult = associateCategoryToService(businessId, input, service);

        if (categoryResult.isError()) {
            return error(categoryResult.getErrors());
        }

        serviceMapper.fillEntityFromInput(service, input);
        var savedService = saveService.execute(service);

        return success(serviceMapper.toOutput(savedService.getData()));
    }

    private OutputResponse<Void> associateCategoryToService(String businessId,
                                                            ServiceInput input,
                                                            BusinessService service) {
        if (input.getCategoryId() != null && !input.getCategoryId().isBlank()) {
            var categoryResponse = findCategoryById
                    .execute(new WhereBusinessIdAndEntityId(input.getCategoryId(), businessId));

            if (categoryResponse.isError()) {
                return error(categoryResponse.getErrors());
            }

            service.setCategory(categoryResponse.getData());
        }

        return success();
    }

}
