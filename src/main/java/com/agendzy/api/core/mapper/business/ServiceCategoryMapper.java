package com.agendzy.api.core.mapper.business;

import com.agendzy.api.core.domain.business.service.BusinessServiceCategory;
import com.agendzy.api.core.usecase.business.boundary.input.data.service.category.ServiceCategoryInput;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ServiceCategoryMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateServiceCategory(@MappingTarget BusinessServiceCategory serviceCategory, ServiceCategoryInput input);

}
