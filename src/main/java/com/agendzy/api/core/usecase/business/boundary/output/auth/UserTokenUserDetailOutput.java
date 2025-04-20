package com.agendzy.api.core.usecase.business.boundary.output.auth;

public record UserTokenUserDetailOutput(String id,
                                        String name,
                                        String picture,
                                        String whatsapp,
                                        Boolean whatsappConfirmed) {
}
