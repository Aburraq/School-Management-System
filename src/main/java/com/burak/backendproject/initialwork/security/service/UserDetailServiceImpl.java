package com.burak.backendproject.initialwork.security.service;

import com.burak.backendproject.initialwork.entity.concretes.user.User;
import com.burak.backendproject.initialwork.service.helper.MethodHelper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final MethodHelper methodHelper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = methodHelper.loadUserByName(username);

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getIsAdvisor(),
                user.getPassword(),
                user.getSsn(),
                user.getUserRole().getRoleType().getName());
    }
}
