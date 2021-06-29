package com.daas.saas.investment.identity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String tenantId;
    private boolean returnSecureToken = true;
}
