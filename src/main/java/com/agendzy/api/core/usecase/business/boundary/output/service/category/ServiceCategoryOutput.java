package com.agendzy.api.core.usecase.business.boundary.output.service.category;

import com.agendzy.api.core.domain.business.service.BusinessServiceCategory;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceCategoryOutput {

    private String id;
    private String name;
    private boolean active;

    public static ServiceCategoryOutput of(BusinessServiceCategory category) {
        return ServiceCategoryOutput.builder()
                .id(category.getId())
                .name(category.getName())
                .active(category.isActive())
                .build();
    }

}
