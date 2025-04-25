package com.agendzy.api.core.domain.business;

import lombok.Getter;

@Getter
public enum BusinessTeamSize {

    ONE_TO_TWO("1 a 2"),
    THREE_TO_FIVE("3 a 5"),
    SIX_TO_TEN("6 a 10"),
    ELEVEN_OR_MORE("11 ou mais");

    private final String description;

    BusinessTeamSize(String description) {
        this.description = description;
    }

}
