package com.idp.demo.identity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantResponse {
  private String name;
  private String displayName;
  private boolean allowPasswordSignup;
  private boolean enableEmailLinkSignin;
  private boolean disableAuth;
  private boolean enableAnonymousUser;
}
