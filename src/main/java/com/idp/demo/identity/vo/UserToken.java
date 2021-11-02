package com.idp.demo.identity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserToken {
    private String localId;
    private String email;
    private String displayName;
    private String idToken;
    private String refreshToken;
    private boolean registered;
    private String expiresIn;
}
