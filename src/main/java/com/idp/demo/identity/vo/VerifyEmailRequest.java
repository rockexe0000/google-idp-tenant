package com.idp.demo.identity.vo;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyEmailRequest {
  @NotEmpty
  private String requestType = "VERIFY_EMAIL";
  private String idToken;
}
