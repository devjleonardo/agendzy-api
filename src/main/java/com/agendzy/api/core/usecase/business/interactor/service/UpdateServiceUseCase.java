package com.agendzy.api.core.usecase.business.interactor.service;

import com.agendzy.api.core.domain.business.service.BusinessService;
import com.agendzy.api.core.domain.business.service.BusinessServiceCategory;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.mapper.business.ServiceMapper;
import com.agendzy.api.core.usecase.business.boundary.input.data.service.ServiceInput;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessIdAndEntityId;
import com.agendzy.api.core.usecase.business.boundary.output.service.ServiceOutput;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;
import static com.agendzy.api.core.validator.business.BusinessAccessValidator.checkAccessToBusiness;

@Service
@RequiredArgsConstructor
public class UpdateServiceUseCase {

    private final FindOneGateway<BusinessService, WhereBusinessIdAndEntityId> findServiceByBusinessAndId;
    private final FindOneGateway<BusinessServiceCategory, WhereBusinessIdAndEntityId> findCategoryByBusinessAndId;
    private final SaveGateway<BusinessService> saveService;
    private final ServiceMapper serviceMapper;

    public OutputResponse<ServiceOutput> execute(String businessId, String serviceId, ServiceInput input) {
        checkAccessToBusiness(businessId);

        var serviceResponse = findServiceByBusinessAndId.execute(
                new WhereBusinessIdAndEntityId(businessId, serviceId)
        );

        if (serviceResponse.isError()) {
            return error(serviceResponse.getErrors());
        }

        BusinessService service = serviceResponse.getData();

        var updateCategoryResult = updateCategory(businessId, input, service);

        if (updateCategoryResult.isError()) {
            return error(updateCategoryResult.getErrors());
        }

        serviceMapper.updateService(service, input);
        var saved = saveService.execute(service);

        return success(serviceMapper.toOutput(saved.getData()));
    }

    private OutputResponse<Void> updateCategory(String businessId,
                                                ServiceInput input,
                                                BusinessService service) {
        if (input.getCategoryId() == null || input.getCategoryId().isBlank()) {
            return success();
        }

        String currentCategoryId = service.getCategory() != null ? service.getCategory().getId() : null;

        if (currentCategoryId != null && currentCategoryId.equals(input.getCategoryId())) {
            return success();
        }

        var categoryResponse = findCategoryByBusinessAndId.execute(
                new WhereBusinessIdAndEntityId(input.getCategoryId(), businessId)
        );

        if (categoryResponse.isError()) {
            return error(categoryResponse.getErrors());
        }

        service.setCategory(categoryResponse.getData());

        return success();
    }

}
