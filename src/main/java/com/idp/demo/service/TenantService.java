package com.idp.demo.service;


import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.idp.demo.identity.vo.CreateTenantRequest;
import com.idp.demo.identity.vo.CreateTenantUserRequest;
import com.idp.demo.identity.vo.CreateUserResponse;
import com.idp.demo.identity.vo.TenantListResponse;
import com.idp.demo.identity.vo.TenantResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class TenantService {

  private static final String TENANT_API_SUFFIX = "/tenants";
  private GoogleCredentials googleCredentials;
  private RestTemplate restTemplate;
  private String tenantURI;

  @Autowired
  public TenantService(
      GoogleCredentials googleCredentials,
      RestTemplate restTemplate,
      @Value("${gcp.identity.endpoint}") String endpoint,
      @Value("${gcp.identity.project}") String project) {
    this.googleCredentials = googleCredentials;
    this.restTemplate = restTemplate;
    this.tenantURI = endpoint + "projects/" + project + TENANT_API_SUFFIX;
  }

  public TenantResponse createTenant(CreateTenantRequest createTenantRequest) {
    String token = getToken();
    HttpHeaders headers = createAuthHeaders(token);
    HttpEntity<CreateTenantRequest> request = new HttpEntity<>(createTenantRequest, headers);
    ResponseEntity<TenantResponse> response =
        restTemplate.postForEntity(tenantURI, request, TenantResponse.class);
    log.debug("response status: {}", response.getStatusCode());
    return response.getBody();
  }

  public TenantListResponse listTenants() {
    String token = getToken();
    HttpHeaders headers = createAuthHeaders(token);
    ResponseEntity<TenantListResponse> response =
        restTemplate.exchange(
            tenantURI, HttpMethod.GET, new HttpEntity<>(headers), TenantListResponse.class);
    return response.getBody();
  }

  public CreateUserResponse createTenantUser(
      String projectId, String tenantId, CreateTenantUserRequest createTenantRequest) {
    String url =
        "https://identitytoolkit.googleapis.com/v1/projects/"
            + projectId
            + "/tenants/"
            + tenantId
            + "/accounts";
    String token = getToken();
    HttpHeaders headers = createAuthHeaders(token);
    HttpEntity<CreateTenantUserRequest> request = new HttpEntity<>(createTenantRequest, headers);
    ResponseEntity<CreateUserResponse> response =
        restTemplate.postForEntity(url, request, CreateUserResponse.class);
    log.debug("response body: {}", response.getBody());
    return response.getBody();
  }

  public void deleteTenant(String name) {
    String url = tenantURI + "/" + name;
    String token = getToken();
    HttpHeaders headers = createAuthHeaders(token);
    ResponseEntity<Void> response =
        restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(headers), Void.class);
    log.info("response status: {}", response.getStatusCode());
  }

  private HttpHeaders createAuthHeaders(String token) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", "Bearer " + token);
    return headers;
  }

  private String getToken() {
    refreshCredentials();
    AccessToken accessToken = googleCredentials.getAccessToken();
    return accessToken.getTokenValue();
  }

  private void refreshCredentials() {
    try {
      googleCredentials.refreshIfExpired();
    } catch (IOException e) {
      log.warn("Refresh credential failed!", e);
    }
  }
}
