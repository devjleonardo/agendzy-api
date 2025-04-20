package com.agendzy.api.core.usecase.common.interactor;

import com.agendzy.api.core.domain.common.User;
import com.agendzy.api.core.exception.BusinessRuleException;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.gateway.common.SaveGateway;
import com.agendzy.api.core.gateway.common.auth.EncryptPasswordGateway;
import com.agendzy.api.core.usecase.common.boundary.input.data.user.CreateUserInput;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereEmail;
import com.agendzy.api.core.usecase.common.boundary.output.data.DomainError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final FindOneGateway<User, WhereEmail> findUserByEmail;
    private final SaveGateway<User> userSave;
    private final EncryptPasswordGateway passwordEncrypt;

    public User execute(CreateUserInput input) {
        if (isEmailInUse(input.getEmail())) {
            throw new BusinessRuleException(DomainError.USER_ALREADY_EXISTS);
        }

        String password = encryptPassword(input.getPassword());

        User user = new User();
        user.setFullName(input.getName());
        user.setEmail(input.getEmail());
        user.setPhoneNumber(input.getPhoneNumber());
        user.setPassword(password);

        return userSave.execute(user).getData();
    }

    private boolean isEmailInUse(String email) {
        return findUserByEmail.execute(new WhereEmail(email)).isSuccess();
    }

    private String encryptPassword(String rawPassword) {
        return passwordEncrypt.execute(rawPassword);
    }

}