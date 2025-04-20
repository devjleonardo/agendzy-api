package com.agendzy.api.core.usecase.business.boundary.input.query;

import com.agendzy.api.core.usecase.common.boundary.input.query.QueryInput;

import java.time.LocalDate;

public record WhereBusinessIdAndProfessionalIdAndDate(String businessId,
                                                      String professionalId,
                                                      LocalDate date) implements QueryInput {
}
