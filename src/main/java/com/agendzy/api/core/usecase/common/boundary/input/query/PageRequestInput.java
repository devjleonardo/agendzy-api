package com.agendzy.api.core.usecase.common.boundary.input.query;

import lombok.Getter;

@Getter
public final class PageRequestInput {

    private final int page;
    private int pageSize;

    private PageRequestInput(int page, int pageSize) {
        this.page = (page > 0) ? page - 1 : 0;
        this.pageSize = (pageSize < 0) ? 5 : pageSize;
        if (pageSize > 25) {
            this.pageSize = 25;
        }
    }

    public static PageRequestInput of(int page, int pageSize) {
        return new PageRequestInput(page, pageSize);
    }

}
