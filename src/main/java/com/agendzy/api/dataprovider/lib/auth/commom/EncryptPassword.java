package com.agendzy.api.dataprovider.lib.auth.commom;

import com.agendzy.api.core.gateway.common.auth.EncryptPasswordGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EncryptPassword implements EncryptPasswordGateway {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String execute(String password) {
        return passwordEncoder.encode(password);
    }

}
