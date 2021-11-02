package com.idp.demo.identity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTenantRequest {
  private String displayName;
  private boolean allowPasswordSignup;
}
