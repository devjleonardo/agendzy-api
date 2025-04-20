package com.agendzy.api.core.usecase.common.boundary.input.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseQuery {

    private String tenant;
    private String tenantOwner;

}
