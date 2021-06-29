package com.daas.saas.investment.identity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.daas.saas.investment.identity.vo.ChangePasswordRequest;
import com.daas.saas.investment.identity.vo.SignInRequest;
import com.daas.saas.investment.identity.vo.UserToken;
import com.daas.saas.investment.identity.vo.ValidateTokenRequest;
import com.daas.saas.investment.identity.vo.VerifyEmailRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

  private static final String SIGN_IN_URL =
      "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=";
  private static final String CHANGE_PASSWORD_URL =
      "https://identitytoolkit.googleapis.com/v1/accounts:update?key=";
  private static final String SIGN_IN_TOKEN =
      "https://identitytoolkit.googleapis.com/v1/accounts:signInWithCustomToken?key=";
  private static final String VERIFY_EMAIL_URL =
      "https://identitytoolkit.googleapis.com/v1/accounts:sendOobCode?key=";

  @Value("${gcp.identity.apiKey}")
  String apiKey;

  @Autowired
  GcpIdentityService gcpIdentityService;

  @Autowired
  RestTemplate restTemplate;

  public UserToken signIn(SignInRequest signInRequest) {
    HttpEntity<SignInRequest> request = new HttpEntity<>(signInRequest);
    String url = SIGN_IN_URL + apiKey;

    ResponseEntity<UserToken> token = restTemplate.postForEntity(url, request, UserToken.class);
    log.debug("user token: {}", token.getBody());

    return token.getBody();
  }

  public boolean changePassword(ChangePasswordRequest changePasswordRequest) {
    HttpEntity<ChangePasswordRequest> request = new HttpEntity<>(changePasswordRequest);
    String url = CHANGE_PASSWORD_URL + apiKey;
    ResponseEntity<Void> response = restTemplate.postForEntity(url, request, Void.class);
    if (!response.getStatusCode().is2xxSuccessful()) {
      return false;
    }
    return true;
  }


  public boolean validateToken(ValidateTokenRequest validateTokenRequest) {


    HttpEntity<ValidateTokenRequest> request = new HttpEntity<>(validateTokenRequest);


    gcpIdentityService.vaildateUser(request.getBody().getToken());



    // String url = SIGN_IN_TOKEN + apiKey;
    // ResponseEntity<Void> response = restTemplate.postForEntity(url, request, Void.class);
    // if (!response.getStatusCode().is2xxSuccessful()) {
    // return false;
    // }
    return true;
  }

  public boolean verifyEmail(VerifyEmailRequest verifyEmailRequest) {
    HttpEntity<VerifyEmailRequest> request = new HttpEntity<>(verifyEmailRequest);
    String url = VERIFY_EMAIL_URL + apiKey;
    ResponseEntity<Void> response = restTemplate.postForEntity(url, request, Void.class);
    if (!response.getStatusCode().is2xxSuccessful()) {
      return false;
    }
    return true;
  }


}
