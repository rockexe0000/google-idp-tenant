package com.idp.demo.identity.vo;


import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTenantUserRequest {
  @NotEmpty private String email;
  @NotEmpty private String password;
}
