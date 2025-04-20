package com.agendzy.api.core.usecase.common.boundary.output.query;

import java.util.List;

public record PageResultOutput<T>(List<T> data, int page, int pageSize, long count) {
}
