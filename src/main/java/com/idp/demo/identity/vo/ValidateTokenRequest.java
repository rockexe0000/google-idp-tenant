package com.idp.demo.identity.vo;


import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateTokenRequest {
  @NotEmpty private String token;
  private String tenantId;
  private boolean returnSecureToken = true;
}
