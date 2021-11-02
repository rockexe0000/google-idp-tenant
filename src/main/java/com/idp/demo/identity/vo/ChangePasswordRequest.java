package com.idp.demo.identity.vo;


import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
  @NotEmpty private String idToken;
  @NotEmpty private String password;
  private boolean returnSecureToken = true;
}
