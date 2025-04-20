package com.agendzy.api.dataprovider.lib.auth.commom;

import com.agendzy.api.core.gateway.common.auth.ValidatePasswordGateway;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ValidatePassword implements ValidatePasswordGateway {

    private final PasswordEncoder passwordEncoder;

    public ValidatePassword(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean execute(String password, String compare) {
        return passwordEncoder.matches(password, compare);
    }

}
