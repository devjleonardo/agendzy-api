package com.agendzy.api.core.usecase.common.boundary.output;

public class DefaultOutputError implements OutputError {

    private final OutputErrorType type;
    private final String title;
    private final String detail;

    public DefaultOutputError(OutputErrorType type, String title, String detail) {
        this.type = type;
        this.title = title;
        this.detail = detail;
    }

    @Override
    public String getType() {
        return type.name();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDetail() {
        return detail;
    }

}
