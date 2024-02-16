package com.burak.backendproject.initialwork.controller.user;

import com.burak.backendproject.initialwork.payload.request.user.UserRequest;
import com.burak.backendproject.initialwork.payload.response.abstracts.BaseUserResponse;
import com.burak.backendproject.initialwork.payload.response.business.ResponseMessage;
import com.burak.backendproject.initialwork.payload.response.user.UserResponse;
import com.burak.backendproject.initialwork.service.user.UserService;
import com.burak.backendproject.initialwork.payload.request.user.UserRequestWithoutPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize(("hasAnyAuthority('Admin')"))
    @PostMapping("/save/{userRole}")
    public ResponseEntity<ResponseMessage<UserResponse>> saveUser(
            @RequestBody @Valid UserRequest userRequest,
            @PathVariable String userRole
            ){

        return ResponseEntity.ok(userService.saveUser(userRequest, userRole));

    }

    @PreAuthorize(("hasAnyAuthority('Admin')"))
    @GetMapping("/getAllUsersByPage/{userRole}")
    public ResponseEntity<Page<UserResponse>> getUserByPage(
            @PathVariable String userRole,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
            ){

        Page<UserResponse> userResponse = userService.getUsersByPage(page,size,sort,type,userRole);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);

    }

    @PreAuthorize(("hasAnyAuthority('Admin, Dean')"))
    @GetMapping("/getUserById/{userId}")
    public ResponseMessage<BaseUserResponse> getUserById(@PathVariable Long userId){

        return userService.getUserById(userId);

    }

    @PreAuthorize(("hasAnyAuthority('Admin, Dean, ViceDean')"))
    @GetMapping("/getUserByName")
    public List<UserResponse> getUserByName(@RequestParam(name = "name") String userName){

        return userService.getUserByName(userName);

    }

    @PreAuthorize(("hasAnyAuthority('Admin, Dean, ViceDean, Teacher')"))
    @PatchMapping("/updateUser")
    public ResponseEntity<String> updateUser(
            @RequestBody @Valid UserRequestWithoutPassword userRequestWithoutPassword,
            HttpServletRequest request){

        return ResponseEntity.ok(userService.updateUser(userRequestWithoutPassword, request));

    }

    @PreAuthorize(("hasAnyAuthority('Admin')"))
    @PutMapping("/update/{userId}")
    public ResponseMessage<BaseUserResponse> updateAdminDeanViceDeanByAdmin(
            @RequestBody @Valid UserRequest userRequest,
            @PathVariable Long userId){

        return userService.updateAdminDeanViceDeanByAdmin(userId, userRequest);

    }

    @PreAuthorize(("hasAnyAuthority('Admin, Dean, ViceDean')"))
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id,
                                                 HttpServletRequest request){

        return ResponseEntity.ok(userService.deleteUserById(id, request));

    }

}
