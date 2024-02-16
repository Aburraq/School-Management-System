package com.burak.backendproject.initialwork.payload.request.authentication;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {

    @NotBlank(message = "Please provide old password!")
    private String oldPassword;

    @NotBlank(message = "Please provide new password!")
    private String newPassword;

}
