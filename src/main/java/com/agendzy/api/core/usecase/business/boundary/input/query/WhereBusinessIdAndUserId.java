package com.agendzy.api.core.usecase.business.boundary.input.query;

import com.agendzy.api.core.usecase.common.boundary.input.query.QueryInput;

public record WhereBusinessIdAndUserId(String businessId, String userId) implements QueryInput {
}
