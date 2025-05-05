package com.agendzy.api.core.mapper.business;

import com.agendzy.api.core.domain.business.openinghours.BusinessOpeningHours;
import com.agendzy.api.core.usecase.business.boundary.input.data.openinghours.BusinessOpeningHoursInput;
import com.agendzy.api.core.usecase.business.boundary.output.openinghours.OpeningHoursOutput;
import org.mapstruct.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Mapper(
    componentModel = "spring",
    imports = {DayOfWeek.class, LocalTime.class, DateTimeFormatter.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OpeningHoursMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "business", ignore = true)
    @Mapping(target = "dayOfWeek", expression = "java(DayOfWeek.valueOf(input.getDayOfWeek().toUpperCase()))")
    @Mapping(target = "startTime", expression = "java(LocalTime.parse(input.getStartTime()))")
    @Mapping(target = "endTime", expression = "java(LocalTime.parse(input.getEndTime()))")
    BusinessOpeningHours toEntity(BusinessOpeningHoursInput input);

    @Named("toOutput")
    @Mapping(target = "dayOfWeek", expression = "java(entity.getDayOfWeek().toString())")
    @Mapping(target = "startTime", expression = "java(entity.getStartTime().format(DateTimeFormatter.ofPattern(\"HH:mm\")))")
    @Mapping(target = "endTime", expression = "java(entity.getEndTime().format(DateTimeFormatter.ofPattern(\"HH:mm\")))")
    OpeningHoursOutput toOutput(BusinessOpeningHours entity);

    @IterableMapping(qualifiedByName = "toOutput")
    List<OpeningHoursOutput> toOutputList(Collection<BusinessOpeningHours> entities);

}
