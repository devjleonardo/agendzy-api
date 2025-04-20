package com.agendzy.api.core.usecase.business.boundary.input.query;

import com.agendzy.api.core.usecase.common.boundary.input.query.QueryInput;

public record WhereBusinessIdAndInviteEmail(String businessId, String inviteEmail) implements QueryInput {}
