package com.burak.backendproject.initialwork.controller.user;

import com.burak.backendproject.initialwork.payload.request.user.UserRequest;
import com.burak.backendproject.initialwork.payload.response.abstracts.BaseUserResponse;
import com.burak.backendproject.initialwork.payload.response.business.ResponseMessage;
import com.burak.backendproject.initialwork.payload.response.user.UserResponse;
import com.burak.backendproject.initialwork.payload.request.user.UserRequestWithoutPassword;
import com.burak.backendproject.initialwork.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser_shouldReturnSavedUserResponse() {
        UserRequest userRequest = new UserRequest();
        String userRole = "Admin";
        ResponseMessage<UserResponse> mockResponse = new ResponseMessage<>();

        when(userService.saveUser(userRequest, userRole)).thenReturn(mockResponse);

        ResponseEntity<ResponseMessage<UserResponse>> response = userController.saveUser(userRequest, userRole);

        assertEquals(mockResponse, response.getBody());
        verify(userService, times(1)).saveUser(userRequest, userRole);
    }

    @Test
    void getUserByPage_shouldReturnPagedUsers() {
        String userRole = "Admin";
        int page = 0;
        int size = 10;
        String sort = "name";
        String type = "desc";
        Page<UserResponse> mockPage = new PageImpl<>(Collections.emptyList());

        when(userService.getUsersByPage(page, size, sort, type, userRole)).thenReturn(mockPage);

        ResponseEntity<Page<UserResponse>> response = userController.getUserByPage(userRole, page, size, sort, type);

        assertEquals(mockPage, response.getBody());
        verify(userService, times(1)).getUsersByPage(page, size, sort, type, userRole);
    }

    @Test
    void getUserById_shouldReturnUserResponse() {
        Long userId = 1L;
        ResponseMessage<BaseUserResponse> mockResponse = new ResponseMessage<>();

        when(userService.getUserById(userId)).thenReturn(mockResponse);

        ResponseMessage<BaseUserResponse> response = userController.getUserById(userId);

        assertEquals(mockResponse, response);
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void getUserByName_shouldReturnUsers() {
        String userName = "John";
        List<UserResponse> mockUsers = Collections.singletonList(new UserResponse());

        when(userService.getUserByName(userName)).thenReturn(mockUsers);

        List<UserResponse> response = userController.getUserByName(userName);

        assertEquals(mockUsers, response);
        verify(userService, times(1)).getUserByName(userName);
    }

    @Test
    void updateUser_shouldReturnSuccessMessage() {
        UserRequestWithoutPassword request = new UserRequestWithoutPassword();
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        String successMessage = "User updated successfully.";

        when(userService.updateUser(request, httpRequest)).thenReturn(successMessage);

        ResponseEntity<String> response = userController.updateUser(request, httpRequest);

        assertEquals(successMessage, response.getBody());
        verify(userService, times(1)).updateUser(request, httpRequest);
    }

    @Test
    void updateAdminDeanViceDeanByAdmin_shouldReturnUpdatedResponse() {
        Long userId = 1L;
        UserRequest userRequest = new UserRequest();
        ResponseMessage<BaseUserResponse> mockResponse = new ResponseMessage<>();

        when(userService.updateAdminDeanViceDeanByAdmin(userId, userRequest)).thenReturn(mockResponse);

        ResponseMessage<BaseUserResponse> response = userController.updateAdminDeanViceDeanByAdmin(userRequest, userId);

        assertEquals(mockResponse, response);
        verify(userService, times(1)).updateAdminDeanViceDeanByAdmin(userId, userRequest);
    }

    @Test
    void deleteUserById_shouldReturnSuccessMessage() {
        Long userId = 1L;
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        String successMessage = "User deleted successfully.";

        when(userService.deleteUserById(userId, httpRequest)).thenReturn(successMessage);

        ResponseEntity<String> response = userController.deleteUserById(userId, httpRequest);

        assertEquals(successMessage, response.getBody());
        verify(userService, times(1)).deleteUserById(userId, httpRequest);
    }
}
