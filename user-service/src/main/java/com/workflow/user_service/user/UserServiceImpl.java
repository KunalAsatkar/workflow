package com.workflow.user_service.user;

import com.workflow.user_service.security.JwtUtils;
import com.workflow.user_service.user.dto.UserDTO;
import com.workflow.user_service.user.req_res.LoginRequest;
import com.workflow.user_service.user.req_res.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils,
                          AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDTO createUser(User user) {

        User existingUser = userRepository.findByUsername(user.getUsername()).orElse(null);

        if (existingUser != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        log.info("the hashed password is {}", hashedPassword);

        User savedUser = userRepository.save(user);
        return buildUserDTO(savedUser);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try{
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            User existingUser = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);
            if (existingUser == null) {
                throw new UsernameNotFoundException("Invalid username or password");
            }
            String token = jwtUtils.generateToken(existingUser);
            return LoginResponse.builder()
                    .userId(existingUser.getId())
                    .username(existingUser.getUsername())
                    .token(token)
                    .build();
        }
        catch (Exception e) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

    @Override
    public UserDTO findUserById(Long userId) {
        try{
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
           return buildUserDTO(user);
        }
        catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        try{
            User user = userRepository.findByUsername(username).orElse(null);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return buildUserDTO(user);
        }
        catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
    }


    @Override
    public UserDTO increamentUserProjectCnt(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        user.setProjectCnt(user.getProjectCnt() + 1);
        userRepository.save(user);
        return buildUserDTO(user);
    }

    public UserDTO buildUserDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .projectCnt(user.getProjectCnt())
                .build();
    }
}
