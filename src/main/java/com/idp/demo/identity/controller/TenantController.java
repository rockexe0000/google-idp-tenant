package com.idp.demo.identity.controller;

import com.idp.demo.identity.service.TenantService;
import com.idp.demo.identity.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/tenants")
public class TenantController {

    @Autowired
    TenantService tenantService;

    @Operation(summary = "Get all tenants")
    @GetMapping
    public TenantListResponse getAllTenants() {
        return tenantService.listTenants();
    }

    @Operation(summary = "Create a tenant")
    @PostMapping
    public TenantResponse createTenant(@RequestBody CreateTenantRequest createTenantRequest) {
        return tenantService.createTenant(createTenantRequest);
    }

    @Operation(summary = "Create a tenant user")
    @PostMapping("/users/{projectId}/{tenantId}")
    public CreateUserResponse createTenant(@PathVariable String projectId,
                                           @PathVariable String tenantId,
                                           @RequestBody CreateTenantUserRequest createTenantUserRequest) {
        return tenantService.createTenantUser(projectId, tenantId, createTenantUserRequest);
    }

    @Operation(summary = "Delete a tenant")
    @DeleteMapping("/{name}")
    public void signIn(@PathVariable String name) {
        tenantService.deleteTenant(name);
    }
}
