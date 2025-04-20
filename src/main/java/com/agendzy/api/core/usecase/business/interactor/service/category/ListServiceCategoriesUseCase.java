package com.agendzy.api.core.usecase.business.interactor.service.category;

import com.agendzy.api.core.domain.business.service.BusinessServiceCategory;
import com.agendzy.api.core.gateway.common.FindAllWithFilterGateway;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessId;
import com.agendzy.api.core.usecase.business.boundary.output.service.category.ServiceCategoryOutput;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.error;
import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;
import static com.agendzy.api.core.validator.business.BusinessAccessValidator.checkAccessToBusiness;

@Service
@RequiredArgsConstructor
public class ListServiceCategoriesUseCase {

    private final FindAllWithFilterGateway<BusinessServiceCategory, WhereBusinessId> findAllCategoryByBusinessId;

    public OutputResponse<List<ServiceCategoryOutput>> execute(String businessId) {
        checkAccessToBusiness(businessId);

        var categoriesResponse = findAllCategoryByBusinessId.execute(new WhereBusinessId(businessId));

        if (categoriesResponse.isError()) {
            return error(categoriesResponse.getErrors());
        }

        List<ServiceCategoryOutput> outputs = categoriesResponse.getData().stream()
                .map(ServiceCategoryOutput::of)
                .toList();

        return success(outputs);
    }

}
