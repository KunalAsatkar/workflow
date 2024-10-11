package com.workflow.user_service.user.req_res;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class LoginResponse {
    private Long userId;
    private String username;
    private String token;
}
