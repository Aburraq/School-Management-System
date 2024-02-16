package com.burak.backendproject.initialwork.payload.request.user;

import com.burak.backendproject.initialwork.payload.request.abstracts.BaseUserRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UserRequest extends BaseUserRequest {
}
