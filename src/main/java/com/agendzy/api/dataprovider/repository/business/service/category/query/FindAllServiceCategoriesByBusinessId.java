package com.agendzy.api.dataprovider.repository.business.service.category.query;

import com.agendzy.api.core.domain.business.service.BusinessServiceCategory;
import com.agendzy.api.core.gateway.common.FindAllWithFilterGateway;
import com.agendzy.api.core.usecase.business.boundary.input.query.WhereBusinessId;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.dataprovider.repository.business.service.category.ServiceCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponseFactory.success;

@Component
@RequiredArgsConstructor
public class FindAllServiceCategoriesByBusinessId implements
        FindAllWithFilterGateway<BusinessServiceCategory, WhereBusinessId> {

    private final ServiceCategoryRepository repository;

    @Override
    public OutputResponse<List<BusinessServiceCategory>> execute(WhereBusinessId where) {
        List<BusinessServiceCategory> categories = repository.findAllByBusinessId(where.businessId());
        return success(categories);
    }

}
