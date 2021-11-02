package com.idp.demo.identity.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.idp.demo.identity.service.GcpIdentityService;
import com.idp.demo.identity.service.UserService;
import com.idp.demo.identity.vo.ChangePasswordRequest;
import com.idp.demo.identity.vo.CreateUserRequest;
import com.idp.demo.identity.vo.SignInRequest;
import com.idp.demo.identity.vo.User;
import com.idp.demo.identity.vo.UserToken;
import com.idp.demo.identity.vo.ValidateTokenRequest;
import com.idp.demo.identity.vo.VerifyEmailRequest;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  GcpIdentityService gcpIdentityService;

  @GetMapping
  public User getUser(@RequestParam(value = "email", defaultValue = "") String email) {
    return gcpIdentityService.getUser(email);
  }

  @PostMapping("/signin")
  public UserToken signIn(@Valid @RequestBody SignInRequest signInRequest) {
    return userService.signIn(signInRequest);
  }

  @PutMapping("/password")
  public ResponseEntity<Void> updatePassword(
      @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
    Boolean result = userService.changePassword(changePasswordRequest);
    if (result.equals(true)) {
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @PutMapping("/validateToken")
  public ResponseEntity<Void> validateToken(
      @Valid @RequestBody ValidateTokenRequest validateTokenRequest) {
    Boolean result = userService.validateToken(validateTokenRequest);
    if (result.equals(true)) {
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @PutMapping("/verifyEmail")
  public ResponseEntity<Void> verifyEmail(
      @Valid @RequestBody VerifyEmailRequest verifyEmailRequest) {
    Boolean result = userService.verifyEmail(verifyEmailRequest);
    if (result.equals(true)) {
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }


  @PostMapping
  public User createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
    return gcpIdentityService.createUser(createUserRequest);
  }

  @DeleteMapping("/{id}")
  public void signIn(@PathVariable String id) {
    gcpIdentityService.deleteUser(id);
  }
}
