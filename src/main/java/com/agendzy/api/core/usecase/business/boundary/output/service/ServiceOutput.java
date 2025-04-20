package com.agendzy.api.core.usecase.business.boundary.output.service;

import com.agendzy.api.core.domain.business.professional.Professional;
import com.agendzy.api.core.usecase.business.boundary.output.service.category.ServiceCategoryOutput;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceOutput {

    private String id;

    private String name;

    private String description;

    private long durationInMinutes;

    private Double price;

    private Double promotionalPrice;

    private String colorHex;

    private ServiceCategoryOutput category;

    private Set<String> photoUrls;

    private List<ProfessionalOutput> professionals;

    @Getter
    @Builder
    public static class ProfessionalOutput {
        private String id;
        private String name;

        public static ProfessionalOutput of(Professional professional) {
            return ProfessionalOutput.builder()
                    .id(professional.getId())
                    .name(professional.getName())
                    .build();
        }
    }

}
