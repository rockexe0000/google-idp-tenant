package com.idp.demo.identity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    @NotEmpty
    private String idToken;
    @NotEmpty
    private String password;
    private boolean returnSecureToken = true;
}
