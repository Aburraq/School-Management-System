package com.burak.backendproject.initialwork.service.user;

import com.burak.backendproject.initialwork.exception.ResourceNotFoundException;
import com.burak.backendproject.initialwork.payload.messages.ErrorMessages;
import com.burak.backendproject.initialwork.entity.concretes.user.UserRole;
import com.burak.backendproject.initialwork.entity.enums.RoleType;
import com.burak.backendproject.initialwork.repository.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRole getUserRole(RoleType roleType){
        return userRoleRepository.findByEnumRoleEquals(roleType)
                .orElseThrow(()-> new ResourceNotFoundException(ErrorMessages.ROLE_NOT_FOUND));

    }

    public List<UserRole> getAllUserRole(){
        return userRoleRepository.findAll();

    }

}
