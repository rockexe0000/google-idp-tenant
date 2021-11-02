package com.idp.demo.identity.vo;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
  @Email private String email;
  @NotBlank private String password;
  @NotBlank private String tenantId;
  private boolean returnSecureToken = true;
}
