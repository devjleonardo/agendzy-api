package com.agendzy.api.core.gateway.common.auth;

public interface ValidatePasswordGateway {

    boolean execute(String password, String compare);

}
