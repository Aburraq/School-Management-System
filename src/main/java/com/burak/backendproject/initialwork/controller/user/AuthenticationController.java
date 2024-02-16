package com.burak.backendproject.initialwork.controller.user;

import com.burak.backendproject.initialwork.payload.response.authentication.AuthResponse;
import com.burak.backendproject.initialwork.payload.response.user.UserResponse;
import com.burak.backendproject.initialwork.payload.messages.SuccessMessages;
import com.burak.backendproject.initialwork.payload.request.authentication.LoginRequest;
import com.burak.backendproject.initialwork.payload.request.authentication.UpdatePasswordRequest;
import com.burak.backendproject.initialwork.service.user.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser (@RequestBody @Valid LoginRequest loginRequest){

        return ResponseEntity.ok(authenticationService.authenticateUser(loginRequest));

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher','Student')")
    @PatchMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest,
                                                 HttpServletRequest httpServletRequest){

        authenticationService.updatePassword(updatePasswordRequest, httpServletRequest);

        return ResponseEntity.ok(SuccessMessages.PASSWORD_CHANGED_RESPONSE_MESSAGE);

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher','Student')")
    @GetMapping("/user")
    public ResponseEntity<UserResponse> findByUsername(HttpServletRequest httpServletRequest){

        return ResponseEntity.ok(authenticationService.findByUsername(httpServletRequest));

    }

}
