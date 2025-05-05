package com.agendzy.api.core.usecase.business.boundary.output;

import com.agendzy.api.core.domain.business.Business;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessCreatedOutput {

    private String id;
    private String name;
    private String segment;
    private String ownerName;

    public static BusinessCreatedOutput of(Business business) {
        return BusinessCreatedOutput.builder()
                .id(business.getId())
                .name(business.getName())
                .segment(business.getSegment().name())
                .ownerName(business.getOwner().getName())
                .build();
    }

}
