package com.burak.backendproject.initialwork.payload.mappers;

import com.burak.backendproject.initialwork.payload.request.abstracts.BaseUserRequest;
import com.burak.backendproject.initialwork.payload.response.user.StudentResponse;
import com.burak.backendproject.initialwork.payload.response.user.UserResponse;
import com.burak.backendproject.initialwork.entity.concretes.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User mapUserRequestToUser(BaseUserRequest userRequest){
        return User.builder()
                .username(userRequest.getUsername())
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .ssn(userRequest.getSsn())
                .birthDay(userRequest.getBirthDay())
                .birthPlace(userRequest.getBirthPlace())
                .phoneNumber(userRequest.getPhoneNumber())
                .gender(userRequest.getGender())
                .email(userRequest.getEmail())
                .builtIn(userRequest.getBuiltIn())
                .isAdvisor(false)
                .build();

    }

    public UserResponse mapUserToUserResponse(User user){

        return UserResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .birthDay(user.getBirthDay())
                .birthPlace(user.getBirthPlace())
                .ssn(user.getSsn())
                .email(user.getEmail())
                .userRole(user.getUserRole().getRoleType().name)
                .build();

    }

    public StudentResponse mapUserToStudentResponse(User user){

        return StudentResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .birthDay(user.getBirthDay())
                .birthPlace(user.getBirthPlace())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .email(user.getEmail())
                .fatherName(user.getFatherName())
                .motherName(user.getMotherName())
                .studentNumber(user.getStudentNumber())
                .isActive(user.isActive())
                .build();

    }

}
