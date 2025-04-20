package com.agendzy.api.core.usecase.customer.boundary.output.data;

import com.agendzy.api.core.domain.common.User;
import com.agendzy.api.core.domain.customer.Customer;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerOutput {

    private String id;
    private String fullName;
    private String phoneNumber;
    private String email;

    public static CustomerOutput of(Customer customer) {
        User user = customer.getUser();

        return CustomerOutput.builder()
                .id(customer.getId())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .build();
    }
}
