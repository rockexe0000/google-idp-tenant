package com.idp.demo.identity.vo;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantListResponse {
  private List<TenantResponse> tenants;
}
