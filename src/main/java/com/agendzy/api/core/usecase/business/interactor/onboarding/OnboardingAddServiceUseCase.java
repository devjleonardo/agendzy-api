package com.agendzy.api.core.usecase.business.interactor.onboarding;

import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.domain.business.service.BusinessService;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.mapper.business.ServiceMapper;
import com.agendzy.api.core.usecase.business.boundary.input.data.service.ServiceInput;
import com.agendzy.api.core.usecase.business.boundary.output.service.ServiceOutput;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;
import static com.agendzy.api.core.validator.business.BusinessAccessValidator.checkAccessAndGetBusiness;

@Service
@RequiredArgsConstructor
public class OnboardingAddServiceUseCase {

    private final FindOneGateway<Business, WhereId> findBusinessById;
    private final SaveGateway<Business> businessSave;
    private final ServiceMapper serviceMapper;

    public OutputResponse<List<ServiceOutput>> execute(String businessId, List<ServiceInput> inputs) {
        Business business = getAuthorizedBusiness(businessId);

        inputs.stream()
            .map(this::toValidService)
            .flatMap(Optional::stream)
            .forEach(business::addService);

        Business savedBusiness = businessSave.execute(business).getData();

        return success(serviceMapper.toOutputList(savedBusiness.getServices()));
    }

    private Business getAuthorizedBusiness(String businessId) {
        return checkAccessAndGetBusiness(businessId, findBusinessById).getData();
    }

    private Optional<BusinessService> toValidService(ServiceInput input) {
        if (isInvalid(input)) {
            return Optional.empty();
        }

        var service = new BusinessService();

        serviceMapper.fillEntityFromInput(service, input);

        return Optional.of(service);
    }

    private boolean isInvalid(ServiceInput input) {
        return input.getName() == null
            || input.getPrice() == null
            || input.getDurationInMinutes() == null;
    }

}
