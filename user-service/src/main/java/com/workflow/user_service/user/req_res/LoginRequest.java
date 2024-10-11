package com.workflow.user_service.user.req_res;

import lombok.Data;

@Data
public class LoginRequest {
    String username;
    String password;
}
