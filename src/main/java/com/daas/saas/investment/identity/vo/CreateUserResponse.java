package com.daas.saas.investment.identity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse {
    private String idToken;
    private String localId;
    private String email;
    private String displayName;
    private String refreshToken;
    private String expiresIn;
}
