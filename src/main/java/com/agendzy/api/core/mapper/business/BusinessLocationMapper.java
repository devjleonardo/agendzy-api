package com.agendzy.api.core.mapper.business;

import com.agendzy.api.core.domain.business.BusinessLocation;
import com.agendzy.api.core.usecase.business.boundary.input.data.location.BusinessLocationInput;
import com.agendzy.api.core.usecase.business.boundary.output.location.BusinessLocationOutput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusinessLocationMapper {

    BusinessLocation toEntity(BusinessLocationInput input);

    BusinessLocationOutput toOutput(BusinessLocation entity);

}
