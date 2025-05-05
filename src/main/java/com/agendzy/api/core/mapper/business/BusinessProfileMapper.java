package com.agendzy.api.core.mapper.business;

import com.agendzy.api.core.usecase.business.boundary.output.profile.BusinessProfileOutput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusinessProfileMapper {

    BusinessProfileOutput toOutput(String profile);

}
