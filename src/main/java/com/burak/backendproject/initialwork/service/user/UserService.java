package com.burak.backendproject.initialwork.service.user;

import com.burak.backendproject.initialwork.payload.messages.ErrorMessages;
import com.burak.backendproject.initialwork.payload.request.user.UserRequest;
import com.burak.backendproject.initialwork.payload.response.abstracts.BaseUserResponse;
import com.burak.backendproject.initialwork.payload.response.business.ResponseMessage;
import com.burak.backendproject.initialwork.payload.response.user.UserResponse;
import com.burak.backendproject.initialwork.repository.user.UserRepository;
import com.burak.backendproject.initialwork.service.validator.UniquePropertyValidator;
import com.burak.backendproject.initialwork.entity.concretes.user.User;
import com.burak.backendproject.initialwork.entity.enums.RoleType;
import com.burak.backendproject.initialwork.exception.BadRequestException;
import com.burak.backendproject.initialwork.exception.ResourceNotFoundException;
import com.burak.backendproject.initialwork.payload.mappers.UserMapper;
import com.burak.backendproject.initialwork.payload.messages.SuccessMessages;
import com.burak.backendproject.initialwork.payload.request.user.UserRequestWithoutPassword;
import com.burak.backendproject.initialwork.service.helper.MethodHelper;
import com.burak.backendproject.initialwork.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;
    private final PageableHelper pageableHelper;
    private final MethodHelper methodHelper;

    public ResponseMessage<UserResponse> saveUser(UserRequest userRequest, String userRole) {
        uniquePropertyValidator.checkDuplicate(userRequest.getUsername(),
                userRequest.getSsn(),
                userRequest.getPhoneNumber(),
                userRequest.getEmail());

        User user = userMapper.mapUserRequestToUser(userRequest);

        if (userRole.equalsIgnoreCase(RoleType.ADMIN.getName())) {

            if (Objects.equals(userRequest.getUsername(), "Admin")) {
                user.setBuiltIn(true);
            }

            user.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));

        } else if (userRole.equalsIgnoreCase("Dean")) {

            user.setUserRole(userRoleService.getUserRole(RoleType.MANAGER));

        } else if (userRole.equalsIgnoreCase("ViceDean")) {

            user.setUserRole(userRoleService.getUserRole(RoleType.ASSISTANT_MANAGER));

        } else
            throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_USER_ROLE_MESSAGE, userRole));

        User savedUser = userRepository.save(user);

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_CREATE)
                .returnBody(userMapper.mapUserToUserResponse(savedUser))
                .build();

    }

    public Page<UserResponse> getUsersByPage(int page, int size, String sort, String type, String userRole) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        return userRepository.findByUserByRole(userRole, pageable).map(userMapper::mapUserToUserResponse);

    }

    public ResponseMessage<BaseUserResponse> getUserById(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, userId))
        );

        return ResponseMessage.<BaseUserResponse>builder()
                .message(SuccessMessages.USER_FOUND)
                .returnBody(userMapper.mapUserToUserResponse(user))
                .httpStatus(HttpStatus.OK)
                .build();


    }

    public List<UserResponse> getUserByName(String userName) {

        return userRepository.findUserByNameContainingIgnoreCase(userName).stream()
                .map(userMapper::mapUserToUserResponse).collect(Collectors.toList());

    }

    public String updateUser(UserRequestWithoutPassword userRequest, HttpServletRequest request) {

        String userName = (String) request.getAttribute("username");

        User user = userRepository.findByUsername(userName);

        methodHelper.checkBuiltIn(user);

        uniquePropertyValidator.checkUniqueProperties(user, userRequest);

        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setUsername(userRequest.getUsername());
        user.setBirthDay(userRequest.getBirthDay());
        user.setBirthPlace(userRequest.getBirthPlace());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setGender(userRequest.getGender());
        user.setSsn(userRequest.getSsn());

        userRepository.save(user);
        return SuccessMessages.USER_UPDATE;


    }

    public ResponseMessage<BaseUserResponse> updateAdminDeanViceDeanByAdmin(
            Long userId, UserRequest userRequest) {

        User user = methodHelper.isUserExist(userId);

        methodHelper.checkBuiltIn(user);

        uniquePropertyValidator.checkUniqueProperties(user, userRequest);

        User userToSave = userMapper.mapUserRequestToUser(userRequest);
        userToSave.setId(user.getId());
        userToSave.setUserRole(user.getUserRole());
        User savedUser = userRepository.save(userToSave);

        return ResponseMessage.<BaseUserResponse>builder()
                .message(SuccessMessages.USER_UPDATE_MESSAGE)
                .httpStatus(HttpStatus.OK)
                .returnBody(userMapper.mapUserToUserResponse(savedUser))
                .build();


    }

    public String deleteUserById(Long id, HttpServletRequest request) {

        User user = methodHelper.isUserExist(id);

        String userName = (String) request.getAttribute("username");

        User loggedInUser = userRepository.findByUsername(userName);

        RoleType loggedInUserRole = loggedInUser.getUserRole().getRoleType();
        RoleType deletedUserRole = user.getUserRole().getRoleType();


        if (loggedInUser.getBuiltIn()) {

            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);

        } else if (loggedInUserRole == RoleType.MANAGER) {

            if (!(deletedUserRole == RoleType.TEACHER ||
                  deletedUserRole == RoleType.STUDENT ||
                  deletedUserRole == RoleType.ASSISTANT_MANAGER)) {

                throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);

            }} else if (loggedInUserRole == RoleType.ASSISTANT_MANAGER) {

                if (!(deletedUserRole == RoleType.TEACHER ||
                      deletedUserRole == RoleType.STUDENT)) {

                    throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);

                }
            }
        userRepository.deleteById(id);
        return SuccessMessages.USER_DELETE;

    }

    public List<User> getAllUsers(){

        return userRepository.findAll();

    }

}
