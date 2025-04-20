package com.agendzy.api.core.usecase.common.boundary.output.data.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserOutput {

    private String id;
    private String name;
    private String email;
    private String phone;
    private String whatsappCode;
    private Long whatsappCodeTimeLimit;
    private Boolean whatsappConfirmed;
    private String workspaceId;

//    public static UserOutput of(User user, String workspaceId) {
//        return UserOutput.builder()
//                .id(user.getId())
//                .name(user.getName())
//                .email(user.getEmail())
//                .phone(user.getWhatsappNumber())
//                .whatsappCode(user.getWhatsappCode())
//                .whatsappCodeTimeLimit(user.getWhatsappCodeTimeLimit())
//                .whatsappConfirmed(user.getWhatsappConfirmed())
//                .workspaceId(workspaceId)
//                .build();
//    }

}
