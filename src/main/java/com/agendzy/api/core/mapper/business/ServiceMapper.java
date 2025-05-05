package com.agendzy.api.core.mapper.business;

import com.agendzy.api.core.domain.business.service.BusinessService;
import com.agendzy.api.core.usecase.business.boundary.input.data.service.ServiceInput;
import com.agendzy.api.core.usecase.business.boundary.output.service.ServiceOutput;
import org.mapstruct.*;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

@Mapper(
        componentModel = "spring",
        imports = Duration.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ServiceMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "business", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "duration", expression = "java(Duration.ofMinutes(input.getDurationInMinutes()))")
    void fillEntityFromInput(@MappingTarget BusinessService entity, ServiceInput input);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateService(@MappingTarget BusinessService service, ServiceInput input);

    @Named("toOutput")
    @Mapping(target = "durationInMinutes", expression = "java(service.getDuration() != null ? service.getDuration().toMinutes() : 0L)")
    ServiceOutput toOutput(BusinessService service);

    @IterableMapping(qualifiedByName = "toOutput")
    List<ServiceOutput> toOutputList(Collection<BusinessService> services);

}

