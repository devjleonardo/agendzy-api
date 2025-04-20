package com.agendzy.api.core.domain.customer.auth;

import com.agendzy.api.core.usecase.customer.boundary.output.data.auth.AuthCustomerTokenDataOutput;

public abstract class CustomerContextCurrent {

    private static final ThreadLocal<CustomerContext> CONTEXT = ThreadLocal.withInitial(() ->
        new CustomerContext(AuthCustomerTokenDataOutput.empty()));

    private CustomerContextCurrent() {}

    public static void set(CustomerContext context) {
        CONTEXT.set(context);
    }

    public static String getCustomerIdId() {
        return CONTEXT.get().getCustomerId();
    }

    public static String getEmail() {
        return CONTEXT.get().getEmail();
    }

    public static void clear() {
        CONTEXT.remove();
    }

}
