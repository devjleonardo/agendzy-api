package com.agendzy.api.core.usecase.common.boundary.output.data.user.auth;

public record UserTokenUserDetailOutput(String id,
                                        String name,
                                        String picture,
                                        String whatsapp,
                                        Boolean whatsappConfirmed) {
}
