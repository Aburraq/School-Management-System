package com.burak.backendproject.initialwork.service.helper;

import com.burak.backendproject.initialwork.exception.ConflictException;
import com.burak.backendproject.initialwork.payload.messages.ErrorMessages;
import com.burak.backendproject.initialwork.repository.user.UserRepository;
import com.burak.backendproject.initialwork.entity.concretes.user.User;
import com.burak.backendproject.initialwork.entity.enums.RoleType;
import com.burak.backendproject.initialwork.exception.BadRequestException;
import com.burak.backendproject.initialwork.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MethodHelper {

    private final UserRepository userRepository;

    public User isUserExist(Long userId){
        return userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, userId))
        );
    }

    public void checkBuiltIn(User user){

        if (user.getBuiltIn()) throw new BadRequestException(String.format(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE));


    }

    public User loadUserByName(String username){

        User user = userRepository.findByUsername(username);

        if (user == null){

            throw new UsernameNotFoundException(
                    String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE_USERNAME, username));
        }

        return user;

    }

    public void checkIsAdvisor(User user){

        if(!user.getIsAdvisor()){

            throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_ADVISOR_MESSAGE,user.getId()));

        }
    }

    public void checkRole(User user, RoleType roleType){

        if(!user.getUserRole().getRoleType().equals(roleType)){

            throw new ConflictException(ErrorMessages.NOT_HAVE_EXPECTED_ROLE_USER);

        }

    }

    public List<User> getUserList(List<Long> idList){

        return userRepository.findByIdList(idList);

    }


}
