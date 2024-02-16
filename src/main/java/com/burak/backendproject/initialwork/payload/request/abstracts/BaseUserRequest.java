package com.burak.backendproject.initialwork.payload.request.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseUserRequest extends AbstractUserRequest{

    @NotNull(message = "Please enter your password!")
    @Size(min = 8, max = 60, message = "Your password should be at least 8 characters and maximum 60 characters.")
    private String password;

    private Boolean builtIn = false;

}
