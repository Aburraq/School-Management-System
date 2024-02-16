package com.burak.backendproject.initialwork.service.user;

import com.burak.backendproject.initialwork.payload.messages.ErrorMessages;
import com.burak.backendproject.initialwork.payload.response.authentication.AuthResponse;
import com.burak.backendproject.initialwork.payload.response.user.UserResponse;
import com.burak.backendproject.initialwork.entity.concretes.user.User;
import com.burak.backendproject.initialwork.exception.BadRequestException;
import com.burak.backendproject.initialwork.payload.mappers.UserMapper;
import com.burak.backendproject.initialwork.payload.request.authentication.LoginRequest;
import com.burak.backendproject.initialwork.payload.request.authentication.UpdatePasswordRequest;
import com.burak.backendproject.initialwork.repository.user.UserRepository;
import com.burak.backendproject.initialwork.security.jwt.JwtUtils;
import com.burak.backendproject.initialwork.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public AuthResponse authenticateUser(LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token =  jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        String userRole = roles.stream().findFirst().get();

        AuthResponse.AuthResponseBuilder responseBuilder = AuthResponse.builder();
        responseBuilder.username(userDetails.getUsername());
        responseBuilder.token(token);
        responseBuilder.name(userDetails.getName());
        responseBuilder.ssn(userDetails.getSsn());
        responseBuilder.role(userRole);

        return responseBuilder.build();


    }

    public void updatePassword(UpdatePasswordRequest updatePasswordRequest, HttpServletRequest httpServletRequest) {

        String username = (String) httpServletRequest.getAttribute("username");
        User user = userRepository.findByUsername(username);

        if (user.getBuiltIn()){
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }

        if (passwordEncoder.matches(updatePasswordRequest.getNewPassword(), user.getPassword())){

            throw new BadRequestException(ErrorMessages.PASSWORD_SHOULD_NOT_MATCHED);

        }

        user.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));

        userRepository.save(user);

    }

    public UserResponse findByUsername(HttpServletRequest httpServletRequest){

        String userName = (String) httpServletRequest.getAttribute("username");

        User user = userRepository.findByUsername(userName);

        return userMapper.mapUserToUserResponse(user);

    }

}
