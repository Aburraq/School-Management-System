package com.burak.backendproject.initialwork.service.validator;

import com.burak.backendproject.initialwork.payload.messages.ErrorMessages;
import com.burak.backendproject.initialwork.entity.concretes.user.User;
import com.burak.backendproject.initialwork.exception.ConflictException;
import com.burak.backendproject.initialwork.payload.request.abstracts.AbstractUserRequest;
import com.burak.backendproject.initialwork.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {

    private final UserRepository userRepository;

    public void checkUniqueProperties(User user, AbstractUserRequest userRequest){

        String updatedUsername = "";
        String updatedSsn = "";
        String updatedEmail = "";
        String updatedPhone = "";
        boolean isChanged = false;

        if (!user.getUsername().equalsIgnoreCase(userRequest.getUsername())){

            updatedUsername = userRequest.getUsername();
            isChanged = true;

        }

        if (!user.getSsn().equalsIgnoreCase(userRequest.getSsn())){

            updatedSsn = userRequest.getSsn();
            isChanged = true;

        }

        if (!user.getEmail().equalsIgnoreCase(userRequest.getEmail())){

            updatedEmail = userRequest.getEmail();
            isChanged = true;

        }

        if (!user.getPhoneNumber().equalsIgnoreCase(userRequest.getPhoneNumber())){

            updatedPhone = userRequest.getPhoneNumber();
            isChanged = true;

        }

        if (isChanged){

            checkDuplicate(updatedUsername, updatedSsn, updatedPhone, updatedEmail);

        }


    }

    public void checkDuplicate(String username, String ssn, String phoneNumber, String email){

        if (userRepository.existsByUsername(username)) throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_USERNAME, username));

        if (userRepository.existsBySsn(ssn)) throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_SSN, ssn));

        if (userRepository.existsByPhoneNumber(phoneNumber)) throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, phoneNumber));

        if (userRepository.existsByEmail(email)) throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL, email));

    }

}
