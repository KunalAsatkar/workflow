package com.workflow.user_service.user;

import com.workflow.user_service.user.dto.UserDTO;
import com.workflow.user_service.user.req_res.LoginRequest;
import com.workflow.user_service.user.req_res.LoginResponse;

public interface UserService {

    UserDTO createUser(User user);

    LoginResponse login(LoginRequest loginRequest);

    UserDTO findUserById(Long id);

    UserDTO findUserByUsername(String username);

    UserDTO increamentUserProjectCnt(Long userId);

}