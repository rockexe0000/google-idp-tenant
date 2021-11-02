package com.idp.demo.identity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTenantUserRequest {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
