package com.idp.demo.service;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.idp.demo.exception.IdentityOperationException;
import com.idp.demo.identity.vo.CreateUserRequest;
import com.idp.demo.identity.vo.User;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GcpIdentityService {

  private FirebaseApp firebaseApp;
  private FirebaseAuth firebaseAuth;

  @Autowired
  public GcpIdentityService(
      @Value("${gcp.identity.authDomain}") String authDomain,
      @Value("${spring.application.name}") String appName,
      Environment env)
      throws IOException {
    FirebaseOptions options =
        FirebaseOptions.builder()
            .setCredentials(
                GoogleCredentials.fromStream(
                    new FileInputStream(env.getProperty("GOOGLE_APPLICATION_CREDENTIALS"))))
            .setDatabaseUrl(authDomain)
            .build();
    firebaseApp = FirebaseApp.initializeApp(options, appName);
    log.info("FirebaseApp name: {}", firebaseApp.getName());

    firebaseAuth = FirebaseAuth.getInstance(firebaseApp);
  }

  public Optional<String> createCustomToken(String uid) {
    try {
      return Optional.ofNullable(firebaseAuth.createCustomToken(uid));
    } catch (FirebaseAuthException e) {
      log.error("Create custom token error, uid: {}", uid);
      return Optional.empty();
    }
  }

  public User getUser(String email) {
    UserRecord userRecord = null;
    try {
      userRecord = getFirebaseAuth().getUserByEmail(email);
    } catch (FirebaseAuthException e) {
      log.error("Get user failed, email: {}", email, e);
      throw new IdentityOperationException(e.getMessage());
    }

    User user = transformTo(userRecord);
    log.debug("Get user data: {}", user);

    return user;
  }

  private User transformTo(UserRecord userRecord) {
    return User.builder()
        .email(userRecord.getEmail())
        .displayName(userRecord.getDisplayName())
        .uid(userRecord.getUid())
        .build();
  }

  public User createUser(CreateUserRequest createUserRequest) {
    UserRecord.CreateRequest request =
        new UserRecord.CreateRequest()
            .setEmail(createUserRequest.getEmail())
            .setEmailVerified(false)
            .setPassword(createUserRequest.getPassword())
            .setDisplayName(createUserRequest.getDisplayName())
            .setDisabled(false);

    UserRecord userRecord = null;
    try {
      userRecord = firebaseAuth.createUser(request);
    } catch (FirebaseAuthException e) {
      log.error("Create a user failed, email: {}", createUserRequest.getEmail(), e);
      throw new IdentityOperationException(e.getMessage());
    }
    log.info(
        "Successfully created new user: {}, uid: {}", userRecord.getEmail(), userRecord.getUid());

    return transformTo(userRecord);
  }

  public void deleteUser(String uid) {
    try {
      firebaseAuth.deleteUser(uid);
    } catch (FirebaseAuthException e) {
      log.error("Delete a user failed, uid: {}", uid, e);
      throw new IdentityOperationException(e.getMessage());
    }
  }

  public void vaildateUser(String idToken) {
    try {
      var firebaseToken = firebaseAuth.verifyIdToken(idToken);

      firebaseToken.getClaims();
      firebaseToken.getEmail();
      firebaseToken.getIssuer();
      firebaseToken.getName();
      firebaseToken.getPicture();
      firebaseToken.getUid();

      log.debug("idToken=[" + idToken + "]");
      log.debug("firebaseToken.getClaims()=[" + firebaseToken.getClaims() + "]");
      log.debug("firebaseToken.getEmail()=[" + firebaseToken.getEmail() + "]");
      log.debug("firebaseToken.getIssuer()=[" + firebaseToken.getIssuer() + "]");
      log.debug("firebaseToken.getName()=[" + firebaseToken.getName() + "]");
      log.debug("firebaseToken.getPicture()=[" + firebaseToken.getPicture() + "]");
      log.debug("firebaseToken.getUid()=[" + firebaseToken.getUid() + "]");

    } catch (FirebaseAuthException e) {
      log.error("Vaildate a user failed, idToken: {}", idToken, e);
      throw new IdentityOperationException(e.getMessage());
    }
  }

  public FirebaseApp getFirebaseApp() {
    return firebaseApp;
  }

  public FirebaseAuth getFirebaseAuth() {
    return firebaseAuth;
  }
}
