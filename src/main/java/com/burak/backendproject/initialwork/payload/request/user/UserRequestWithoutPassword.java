package com.burak.backendproject.initialwork.payload.request.user;

import com.burak.backendproject.initialwork.payload.request.abstracts.AbstractUserRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserRequestWithoutPassword extends AbstractUserRequest {



}
