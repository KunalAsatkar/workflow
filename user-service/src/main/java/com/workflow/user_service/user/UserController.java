package com.workflow.user_service.user;

import com.workflow.user_service.user.dto.UserDTO;
import com.workflow.user_service.user.req_res.LoginRequest;
import com.workflow.user_service.user.req_res.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.workflow.user_service.security.JwtUtils;

@RestController
@RequestMapping("auth/user")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("hello")
    public String hello() {
        return "hello world";
    }

    @PostMapping("signup")
    public ResponseEntity<UserDTO> signup(@RequestBody User user) {
        log.info("Signup user: {}", user);
        try {
            UserDTO savedUserDTO = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        log.info("Login user: {}", loginRequest);
        try {
            LoginResponse loginResponse = userService.login(loginRequest);
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            log.error("Login failed {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
        try{
            UserDTO userDTO = userService.findUserById(userId);
            return ResponseEntity.ok(userDTO);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("validate")
    public ResponseEntity<Boolean> validate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok(true);
        } else {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }

    @GetMapping("username/{email}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String email) {
        try{
            UserDTO userDTO = userService.findUserByUsername(email);
            return ResponseEntity.ok(userDTO);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
